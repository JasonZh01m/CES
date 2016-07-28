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

import com.jsscom.ces.data.dao.MediaDao;
import com.jsscom.ces.model.Media;
import com.jsscom.ces.service.MediaService;
import com.jsscom.ces.vo.UploadResultModel;
import com.sq.core.service.impl.ServiceImpl;
import com.sq.core.vo.ResultModel;

@Service
public class MediaServiceImpl extends ServiceImpl<Media> implements
		MediaService {

	@Resource
	private MediaDao mediaDao;
	protected final Log log = LogFactory.getLog(MediaServiceImpl.class);

	@Override
	public ResultModel deleteFile(String id) {
		ResultModel rm = new ResultModel();
		String[] ids = StringUtils.split(id, ",");
		for (int i = 0; i < ids.length; i++) {
			int intId = Integer.parseInt(ids[i]);
			Media media = this.mediaDao.queryById("queryById".intern(), intId);
			if (media != null) {
				int rs = this.mediaDao.deleteById("deleteById".intern(), intId);
				if (rs > 0) {
					rm.setSuccess(0);
					File f = new File(media.getSavePath());
					f.delete();
				} else {
					rm.setSuccess(1);
				}
			}
		}
		return rm;
	}

	@Override
	public UploadResultModel addFile(String servletContextPath,
			MultipartFile file, Media m) {
		UploadResultModel um = new UploadResultModel();
		InputStream is = null;
		FileInputStream newFIS = null;
		try {
			is = file.getInputStream();
			String newMD5 = DigestUtils.md5Hex(is);
			Media media = this.mediaDao.queryByMD5(newMD5);
			if (media != null) {
				um.setMd5Code(media.getMd5Code());
				um.setDownloadPath(media.getDownloadPath());
				this.log.debug("上传文件已同个文件,无需保存!");
				um.setSuccess(0);
			} else {
				String fileName = file.getOriginalFilename();
				String fileType = file.getContentType();
				String[] filenames = StringUtils.split(fileName, ".".intern());
				if (fileName.indexOf(".".intern()) > -1) {
					String newFileName = UUID.randomUUID().toString()
							.replace("-", "")
							+ ".".intern() + filenames[(filenames.length - 1)];
					String targetFilePath = new File(servletContextPath)
							.getParentFile().getPath();
					File targetFile = new File(targetFilePath
							+ "/uploadFile/media".intern(), newFileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					file.transferTo(targetFile);
					newFIS = new FileInputStream(targetFile);
					String targetMd5 = DigestUtils.md5Hex(newFIS);

					m.setDownloadPath("/downLoad/getMedia.do?code=" + targetMd5);
					m.setMd5Code(targetMd5);
					m.setName(fileName);
					String savePath = targetFilePath
							+ "/uploadFile/media/".intern() + newFileName;
					m.setSavePath(savePath);
					m.setType(fileType);
					int rs = this.mediaDao.insert("save", m);
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
	public List<Media> queryByClient() {
		return this.mediaDao.query("queryByClient", null);
	}
}
