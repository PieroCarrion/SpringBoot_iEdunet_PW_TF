package pe.edu.upc.iedunet.helpers;

public class HourFormatter {
	
	public String HourToString(int hour) {
		if(hour<=2460) {//1350
			Integer H = hour / 100;
			Integer M = hour%100;
			
			String hourFinal; 
			if(H<=9) {
				hourFinal ='0' + H.toString() +':';
				
				if(M<=9) {
					hourFinal += '0' + M.toString();
				}
				else {
					hourFinal += M.toString();
				}
			}
			else
			{
				hourFinal = H.toString() +':';
				if(M<=9) {
					hourFinal += '0' + M.toString();
				}
				else {
					hourFinal += M.toString();
				}
			}

			
			return hourFinal;
		}
		else {
			return "00:00";
		}
	}
	
	public int HourToNumber(String hour) {
		String[] HM = hour.split(":");
		String hora = HM[0] + HM[1];
		
		//int H = hour.split(':')[0];
		return Integer.parseInt(hora);
	}
}

/*
 * AlumnoController
 * AlumnoCursosController
 * AlumnoCalificacionesController
 * AlumnoExamenesController
 * AlumnoRecursosController
 * AlumnoTareasController
 *
 *
 *ProfesorController
 *ProfesorCursosController
 *ProfesorCalificacionesController
 *ProfesorExamenesController
 *ProfesorRecursosController
 *ProfesorTareasController
 */