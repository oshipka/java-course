import java.text.ParseException;

public class Main
{
	private static MatrixTask pt1 = new MatrixTask();
	private static LettersTask pt2 = new LettersTask();
	
	public static void main(String[] args)
	{
		System.out.println("1. Max sums in line:");
		pt1.CompleteTask();
		System.out.println("2. T in words replaced with th:");
		pt2.CompleteTask();
		
		try
		{
			EducationTask.Institution[] institutions = new EducationTask.Institution[]
				{
					new EducationTask.School("Gymnasium Prestige", "Lviv", "1987", 517, 53),
					new EducationTask.School("Solonka School", "Solonka", "1934", 326, 32),
					new EducationTask.University("Ivan Franko national university of Lviv", "Universytetska Street 1 Lviv", "1661", 4, 12),
					new EducationTask.University("Taras Shevchenko national university of Kyiiv", "Volodymyrska Street, 64/13 Kiev", "1834", 4, 13),
					new EducationTask.School("School 1", "Lviv", "1897", 412, 13),
					new EducationTask.School("School 2", "Kyiv", "1998", 201, 16),
					new EducationTask.University("National Technical University of Ukraine Kyiv Polytechnic Institute", "Peremohy Street 37 Kiev", "1898", 1, 5),
					new EducationTask.School("School 3", "Sumy", "1975", 307, 4),
					new EducationTask.University("Sumy State University", "Rymskiy Korsakov Street, 2 Sumy", "1993", 2, 10),
					new EducationTask.University("Borys Grinchenko Kyiv University", "Vorovskogo Street, 18/2 Kiev", "1919", 3, 7)
				};
			
			
			System.out.println("3. List of all institutions:");
			EducationTask.Institution.PrintInst(institutions);
			System.out.println("3.1 Institutions ordered in order of  their foundation time:");
			EducationTask.Institution.OrderByYear(institutions);
			System.out.println("3.2 School with least students:");
			EducationTask.School.LeastStudents(institutions);
			System.out.println("3.3 Universities with given level of accreditation:");
			EducationTask.University.GetAllOfAccLevel(institutions, 4);
			
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
}

