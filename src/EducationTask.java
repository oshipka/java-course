import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EducationTask
{
	
	
	static class Institution
	{
		String name;
		String address;
		Calendar foundationDate = Calendar.getInstance();
		
		Institution() throws ParseException
		{
			name = "";
			address = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
			foundationDate.setTime(sdf.parse("1900"));
		}
		
		Institution(String str_name, String str_addr, String fndD) throws ParseException
		{
			name = str_name;
			address = str_addr;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
			foundationDate.setTime(sdf.parse(fndD));
		}
		
		public static void PrintInst(Institution[] institutions)
		{
			for (Institution institution: institutions)
			{
				System.out.println(institution.ToString());
			}
		}
		
		public static void OrderByYear(Institution[] institutions)
		{
			int len = institutions.length;
			for (int i = 0; i<len; i++)
			{
				for(int j = 0; j<len-1; j++)
				{
					if(institutions[j].foundationDate.get(Calendar.YEAR)>institutions[j+1].foundationDate.get(Calendar.YEAR))
					{
						Institution temp = institutions[j];
						institutions[j] = institutions[j+1];
						institutions[j+1] = temp;
					}
				}
				PrintInst(institutions);
			}
		}
		
		public String ToString()
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
			
			String result = "";
			result += this.name + " "
					+ this.address + " "
					+ sdf.format(this.foundationDate.getTime()) + " ";
			return result;
		}
	}
	
	static class School extends Institution
	{
		Integer studentsAmount;
		Integer number;
		
		
		School() throws ParseException
		{
			super();
			studentsAmount = 0;
			number = 0;
		}
		
		School(String str_name, String str_addr, String fndD, Integer stdAm, Integer num) throws ParseException
		{
			super(str_name, str_addr, fndD);
			studentsAmount = stdAm;
			number = num;
		}
		
		public int GetStudentsAmount()
		{
			return studentsAmount;
		}
		
		public static void LeastStudents(Institution[] institutions) throws ParseException
		{
			School result = new School();
			
			for (Institution institution : institutions)
			{
				if (institution instanceof School)
				{
					School curr = (School) institution;
					if (result.studentsAmount > curr.studentsAmount)
					{
						result = curr;
					}
				}
			}
			System.out.println(result.ToString());
		}
	
		public String ToString()
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
			
			String result = "";
			result += this.name + " "
					+ this.address + " "
					+ sdf.format(this.foundationDate.getTime()) + " "
					+ studentsAmount.toString() + " "
					+ number.toString() + " ";
			return result;
		}
	}
	
	static class University extends Institution
	{
		Integer accreditation;
		Integer facultiesAmount;
		
		public University() throws ParseException
		{
			super();
			accreditation = 0;
			facultiesAmount = 0;
		}
		
		University(String str_name, String str_addr, String fndD, Integer accr, Integer facAm) throws ParseException
		{
			super(str_name, str_addr, fndD);
			accreditation = accr;
			facultiesAmount = facAm;
		}
		
		public int GetAccLevel()
		{
			return accreditation;
		}
		
		public static void GetAllOfAccLevel(Institution[] institutions, int accLevel)
		{
			for (Institution institution : institutions)
			{
				if (institution instanceof University)
				{
					University curr = (University) institution;
					if (curr.accreditation == accLevel)
					{
						System.out.print(curr.ToString() + ", ");
					}
				}
			}
		}
		
		public String ToString()
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
			
			String result = "";
			result += this.name + " "
					+ this.address + " "
					+ sdf.format(this.foundationDate.getTime()) + " "
					+ accreditation.toString() + " "
					+ facultiesAmount.toString() + " ";
			return result;
		}
	}
}
