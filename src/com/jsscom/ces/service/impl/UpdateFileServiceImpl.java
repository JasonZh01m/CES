package com.jsscom.ces.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsscom.ces.data.dao.UpdateFileDao;
import com.jsscom.ces.model.UpdateFile;
import com.jsscom.ces.service.UpdateFileService;
import com.jsscom.ces.vo.UploadResultModel;
import com.sq.core.service.impl.ServiceImpl;
import com.sq.core.vo.PaginQueryResult;
import com.sq.core.vo.ResultModel;

@Service
public class UpdateFileServiceImpl extends ServiceImpl<UpdateFile> implements
		UpdateFileService {
	protected final Log log = LogFactory.getLog(UpdateFileServiceImpl.class);

	@Resource
	private UpdateFileDao updateFileDao;

	@Override
	public ResultModel deleteFile(int id) {
		ResultModel rm = new ResultModel();
		UpdateFile updateFile = this.updateFileDao.queryById(
				"queryById".intern(), id);
		if (updateFile != null) {
			int rs = this.updateFileDao.deleteById("deleteById".intern(), id);
			if (rs > 0) {
				rm.setSuccess(0);

				File f = new File(updateFile.getSavePath());
				f.delete();
			} else {
				rm.setSuccess(1);
			}
		}

		return rm;
	}

	@Override
	public UploadResultModel addFile(String servletContextPath,
			MultipartFile file, UpdateFile updateFiel) {
		UploadResultModel um = new UploadResultModel();
		InputStream is = null;
		FileInputStream newFIS = null;
		try {
			is = file.getInputStream();
			String newMD5 = DigestUtils.md5Hex(is);
			UpdateFile updateFile = this.updateFileDao.queryByMD5(newMD5);
			if (updateFile != null) {
				this.log.debug("上传文件已同个文件,无需保存!");
				um.setSuccess(6);
			} else {
				String fileName = file.getOriginalFilename();
				String fileType = file.getContentType();
				String[] filenames = StringUtils.split(fileName, ".".intern());
				if (fileName.indexOf(".".intern()) > -1) {
					String uuid = UUID.randomUUID().toString().replace("-", "");
					String newFileName = uuid + ".".intern()
							+ filenames[(filenames.length - 1)];
					String targetFilePath = new File(servletContextPath)
							.getParentFile().getPath();

					System.out.println("#########targetFilePath#####"
							+ targetFilePath);
					File targetFile = new File(targetFilePath
							+ "/uploadFile/updateFile".intern(), newFileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					file.transferTo(targetFile);
					newFIS = new FileInputStream(targetFile);
					String targetMd5 = DigestUtils.md5Hex(newFIS);
					updateFiel.setFileMd5(targetMd5);
					updateFiel.setFilePath("/downLoad/getFile.do?code="
							+ targetMd5);
					String savePath = targetFilePath
							+ "/uploadFile/updateFile/".intern() + newFileName;
					updateFiel.setSavePath(savePath);
					updateFiel.setFileType(fileType);

					int rs = this.updateFileDao.insert("save".intern(),
							updateFiel);
					if (rs > 0) {
						um.setSuccess(0);
						this.log.debug("文件保存成功");
					} else {
						um.setSuccess(1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			um.setSuccess(1);
			um.setMsg("上传失败,ERROR:" + e.getMessage());
			this.log.debug("文件上传失败,ERROR:" + e.getMessage());

			if (is != null) {
				try {
					is.close();
				} catch (IOException ie) {
					ie.printStackTrace();
				}
			}
			if (newFIS != null)
				try {
					newFIS.close();
				} catch (IOException ie) {
					ie.printStackTrace();
				}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (newFIS != null) {
				try {
					newFIS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return um;
	}

	@Override
	public List<UpdateFile> queryByClient() {
		return this.updateFileDao.query("queryByClient", null);
	}

	@Override
	public PaginQueryResult<UpdateFile> paginQuery(UpdateFile updateFile,
			int index, int size) {
		PaginQueryResult pqr = new PaginQueryResult();
		int count = this.updateFileDao.count("countUpdate", updateFile);
		if ((count > 0) && (index > 0)) {
			int pageCount = (count - 1) / size + 1;
			pqr.setRows(this.updateFileDao.queryPageList("queryForUpdatePage",
					updateFile, index, size));
			pqr.setTotal(pageCount);
			pqr.setPage(index);
			pqr.setRecords(count);
		}
		return pqr;
	}
}
