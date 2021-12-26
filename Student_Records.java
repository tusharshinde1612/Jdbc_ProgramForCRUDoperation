package jdbcdemo;
import java.util.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;

public class Student_Records  {
	public void show_Records(Statement st) throws Exception {
		ResultSet rs=st.executeQuery("select * from Student");
		while(rs.next()) {
			System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"   "+rs.getDate(3)+
					"   "+rs.getDate(4));
		}
		
	}
	public void insert_Record(PreparedStatement psmt)throws Exception {
		Scanner sc=new Scanner(System.in);
		while (true) {
			System.out.println("Enter Student_No.");
			int Student_No = sc.nextInt();
			System.out.println("Enter Student_Name");
			String Student_Name = sc.next();
			System.out.println("Enter Student_DOB in'dd/MM/yyyy' format");
			String Student_DOB = sc.next();
			SimpleDateFormat sdf1=new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date date1=sdf1.parse(Student_DOB);
			long l=date1.getTime();
			java.sql.Date sdate1=new java.sql.Date(l);
			System.out.println("Enter Student_DOJ in'dd/MM/yyyy' format");
			String Student_DOJ = sc.next();
			SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date date2=sdf2.parse(Student_DOJ);
			long lm=date2.getTime();
			java.sql.Date sdate2=new java.sql.Date(lm);
			psmt.setInt(1,Student_No);
			psmt.setString(2,Student_Name);
			psmt.setDate(3,sdate1);
			psmt.setDate(4,sdate2);
			psmt.executeUpdate();
			System.out.println("one row inserted successfully!!");
			System.out.println("Do you want to insert data[yes/no]");
			String option = sc.next();
			if (option.equalsIgnoreCase("no")) {
				break;
			}
		}
	}
	public void updateData(Statement st) throws Exception {
		
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter your choice for update");
		System.out.println("*******Menu*********");
		System.out.println("1.Update Student Number");
		System.out.println("2.Update Student Name");
		int choice=sc.nextInt();
		switch(choice) {
		case 1:
			System.out.println("Enter New Number of Student:");
			int iNew_Num=sc.nextInt();
			System.out.println("Enter Old Number Student:");
			int iNum=sc.nextInt();
			String sqlquery1=String.format("update student set Student_No = '%d' where Student_No = %d",iNew_Num,iNum);
			int flag=st.executeUpdate(sqlquery1);
			if(flag==0) {
				System.out.println("Student Number Not Found");
			}else {
			System.out.println("Table updated successfully");}
			break;
		case 2:
			System.out.println("Enter New Name:");
			String iName=sc.next();
			System.out.println("Enter Student_No Whose Name Is To Be Updated:");
			int iNo=sc.nextInt();
			String sqlquery2=String.format("update student set Student_Name = '%s' where Student_No = %d",iName,iNo);
			int flag2=st.executeUpdate(sqlquery2);
			if(flag2==0) {
				System.out.println("Student Number Not Found");
			}else {
			System.out.println("Table updated successfully");}
			break;
		default:
				if(choice<1||choice>2) {
					System.out.println("Wrong choice entered");
				}
		}
		

	}
	public void delete_Records(Statement st) throws Exception {
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter Student_No whose record is to be deleted:");
		int num=sc.nextInt();
		String query=String.format("Delete from student where Student_No=%d", num);
		int flag=st.executeUpdate(query);
		if(flag==0) {
			System.out.println("Data Not Found!!..");
		}else {
		System.out.println("Record deleted successfully");}
	}
	public void search_Records(Statement st) throws Exception {
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter Student_No whose record is to be searched:");
		int num=sc.nextInt();
		String query=String.format("Select * from student where Student_No=%d", num);
		ResultSet rs=st.executeQuery(query);
		boolean flag=rs.next();
		if(flag==false) {
			System.out.println("No Data Found!!");
		}else {
		while(rs.next()) {
			System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"   "+rs.getDate(3)+
					"   "+rs.getDate(4));
		}
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_DB","root","root");
		PreparedStatement psmt=con.prepareStatement("insert into student values(?,?,?,?)");
		Statement st=con.createStatement();
		Student_Records sr=new Student_Records();
		Scanner sc= new Scanner(System.in);
		while(true) {
		System.out.println("*******Menu*********");
		System.out.println("1.Insert data");
		System.out.println("2.Update data");
		System.out.println("3.Delete data");
		System.out.println("4.Show all data");
		System.out.println("5.Search for particular data");
		System.out.println("Enter your choice [1/2/3/4/5]");
		int choice=sc.nextInt();
		switch(choice) {
		case 1:
			sr.insert_Record(psmt);
			break;
		case 2:
			sr.updateData(st);
			break;
		case 3:
			sr.delete_Records(st);
			break;
		case 4:
			sr.show_Records(st);
			break;
		case 5:
			sr.search_Records(st);
			break;
		default:
			if(choice<1||choice>5) {
			System.out.println("Wrong choice Entered");}
		}
		System.out.println("Do you want to continue.....[yes/no]");
		String option = sc.next();
		if (option.equalsIgnoreCase("no")) {
			break;
		}
		}
		con.close();

}
}
