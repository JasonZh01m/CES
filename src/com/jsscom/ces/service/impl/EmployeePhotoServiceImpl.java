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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsscom.ces.data.dao.EmployeePhotoDao;
import com.jsscom.ces.model.EmployeePhoto;
import com.jsscom.ces.service.EmployeePhotoService;
import com.jsscom.ces.vo.UploadFileModel;
import com.sq.core.service.impl.ServiceImpl;

@Service
public class EmployeePhotoServiceImpl extends ServiceImpl<EmployeePhoto>
		implements EmployeePhotoService {

	@Resource
	private EmployeePhotoDao employeePhotoDao;

	@Override
	public void deleteFile(String savePath) {
		File f = new File(savePath);
		if (f.exists())
			f.delete();
	}

	@Override
	public List<EmployeePhoto> getEmployee(String employeeNumber) {
		List list = this.employeePhotoDao.getEmployee(employeeNumber);
		return list;
	}

	@Override
	public UploadFileModel fileFile(String md5) {
		UploadFileModel um = null;
		List list = this.employeePhotoDao.findFile(md5);
		if ((list != null) && (list.size() > 0)) {
			EmployeePhoto ep = (EmployeePhoto) list.get(0);
			um = new UploadFileModel();
			if (md5.equalsIgnoreCase(ep.getCertificateMd5())) {
				um.setSavePath(ep.getCertificatePath());
				um.setName(ep.getCertificateName());
			} else {
				um.setSavePath(ep.getHpImgPath());
				um.setName(ep.getHpImgName());
			}
		}
		return um;
	}

	@Override
	public UploadFileModel addFile(String servletContextPath, MultipartFile file) {
		UploadFileModel uf = new UploadFileModel();
		InputStream is = null;
		FileInputStream newFIS = null;
		try {
			is = file.getInputStream();
			String md5Code = DigestUtils.md5Hex(is);
			uf.setMd5Code(md5Code);
			String fileName = file.getOriginalFilename();
			String fileType = file.getContentType();
			String[] filenames = StringUtils.split(fileName, ".".intern());
			if (fileName.indexOf(".".intern()) > -1) {
				String uuid = UUID.randomUUID().toString().replace("-", "");
				String newFileName = uuid + ".".intern()
						+ filenames[(filenames.length - 1)];
				String targetFilePath = new File(servletContextPath)
						.getParentFile().getPath();
				uf.setName(newFileName);
				File targetFile = new File(targetFilePath
						+ "/uploadFile/updateFile".intern(), newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				file.transferTo(targetFile);
				newFIS = new FileInputStream(targetFile);
				String targetMd5 = DigestUtils.md5Hex(newFIS);
				uf.setDownloadPath("/employeePhoto/getFile.do?code="
						+ targetMd5);
				String savePath = targetFilePath
						+ "/uploadFile/updateFile/".intern() + newFileName;
				uf.setSavePath(savePath);
				uf.setType(fileType);
			}
		} catch (Exception e) {
			e.printStackTrace();

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
		return uf;
	}

	public EmployeePhotoDao getEmployeePhotoDao() {
		return this.employeePhotoDao;
	}

	public void setEmployeePhotoDao(EmployeePhotoDao employeePhotoDao) {
		this.employeePhotoDao = employeePhotoDao;
	}
}