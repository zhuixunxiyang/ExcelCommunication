package jsf.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jsf.Global;
import jsf.dao.UserDAO;
import jsf.entity.User;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UserBean {

	private String msg;
	
	private UserDAO userDAO;

	private List<String> userList;
	
	private UploadedFile uploadedFile;
	
	private static String UPLOAD_DIR = Global.getServletContext().getRealPath("/upload");
	
	private static String EXPORT_DIR = Global.getServletContext().getRealPath("/export");
	
	private static DateFormat DATE_FORMAT = new DateFormat("yyyy-MM-dd");
	
	private static WritableCellFormat DATE_CELL_FORMAT = new WritableCellFormat(DATE_FORMAT);
	
	public UserBean () {
		this.userList = new ArrayList<String>(0);
		new File(UPLOAD_DIR).mkdir();
		new File(EXPORT_DIR).mkdir();
	}
	
	public String importFromExcel () {
		InputStream in;
		FileOutputStream fo;
		try {
			in = new BufferedInputStream(this.uploadedFile.getInputStream());
			byte[] buf = new byte[64 * 1024];
			fo = new FileOutputStream(UPLOAD_DIR + "/" + uploadedFile.getName());
			while (in.read(buf) > 0) {
				fo.write(buf);
			}
			in.close();
			fo.close();
		
			Workbook userWorkbook = Workbook.getWorkbook( new File( UPLOAD_DIR + "/" + uploadedFile.getName() ) );
			Sheet sheet = userWorkbook.getSheet(0);
			for(int i = 0; i < sheet.getRows(); i++) {
				String name     = sheet.getCell(0, i).getContents();
				String password = sheet.getCell(1, i).getContents();
				Date birth    = ((DateCell)sheet.getCell(2, i)).getDate();
				
				User userInstance = new User(name, password, birth);
				this.userDAO.save(userInstance);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "userImported";
	}
	
	public void exportToExcel() {
		try {
			File usersFile = new File(EXPORT_DIR + "/users.xls");
			WritableWorkbook userWorkbook = Workbook.createWorkbook( usersFile );
			WritableSheet sheet = userWorkbook.createSheet("Users", 0);
			sheet.addCell(new Label(0, 0, "User"));
			sheet.addCell(new Label(1, 0, "Birth"));
			
			List<User> userList = this.userDAO.findAll();
			for (int i = 0; i < userList.size(); i++) {
				sheet.addCell( new Label( 0, i+1, userList.get(i).getName() ) );
				sheet.addCell( new DateTime(1, i+1, userList.get(i).getBirth(), DATE_CELL_FORMAT ) );
			}
			
			userWorkbook.write();
			userWorkbook.close();
			
			HttpServletResponse servletReponse = Global.getServletReponse();
			servletReponse.setHeader("Content-disposition", "attachment; filename=users.xls");
			servletReponse.setContentLength( (int) usersFile.length() );
			servletReponse.setContentType("application/x-download");
			
			ServletOutputStream servletOutputStream = servletReponse.getOutputStream();
			FileInputStream fis = new FileInputStream(usersFile);
			byte[] buf = new byte[1024];
			while (0 < fis.read(buf)) {
				servletOutputStream.write(buf);
			}
			
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getUserList() {
		List<User> tmpList = this.userDAO.findAll();
		this.userList.clear();
		
		for( User user : tmpList )
			this.userList.add( user.getName() );
		return userList;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}
