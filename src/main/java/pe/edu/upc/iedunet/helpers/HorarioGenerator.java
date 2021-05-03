package pe.edu.upc.iedunet.helpers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.iedunet.viewmodels.HorarioItemViewModel;
import pe.edu.upc.iedunet.viewmodels.HorarioViewModel;

public class HorarioGenerator {
	
	static HourFormatter hourFormatter = new HourFormatter();

	// SE TIENE QUE CONSIDERAR CUANTAS HORAS HAY ENTRE EL HORARIO DE LA PRIMERA
	// HORAR Y DE LA ULTIMA
	// PARA CONOCER CUANTAS FILAS CREAR
	// Ejemplo, si aunque sea un curso tiene una clase pasando las 2, agrego, si
	// todas las clases son antes de las 2. aplico 7 - 14
	// Si varios cursos tienen horarios mas de las 12, empiezo desde ahi
	
	public List<HorarioItemViewModel> GenerateHorario(List<HorarioViewModel> horarios) {
		java.util.Date t= new java.util.Date();
		//System.out.println("Hora inicio: " + t.getTime() + " en milisegundos");
		
		int primerHoraHorario = 700;
		int nFilas = 11;
		int horaMin = primerHoraHorario;
		int horaMax = primerHoraHorario + (nFilas*100);
		///LISTA DE FILAS PARA EL HORARIO
		List<HorarioItemViewModel> horariositems = new ArrayList<HorarioItemViewModel>();

		//Instancio las filas con sus respectivo rango de horas --Esto reemplaza las lineas 465 y 495 del otro metodo
		for(int i=0;i<nFilas;i++) {
			HorarioItemViewModel fila = new HorarioItemViewModel();
			String h1= hourFormatter.HourToString(primerHoraHorario);
			String h2=hourFormatter.HourToString(primerHoraHorario+100);//+100 para simular una hora mas
			String m = primerHoraHorario >=1200 ? "pm" : "am";
			
			fila.setHora(h1 + " - " + h2 + " " + m);
			
			primerHoraHorario +=100;
			horariositems.add(fila);
		}

		//RECORRO LOS HORARIOS Y LOS ASIGNO A CADA FILA
		for (HorarioViewModel h : horarios) {
			for(int i=0; i<nFilas;i++) {
				//System.out.println("I am in horario" + h.getId() + " en la fila " + i);
				//System.out.println("\tHora inicio" + h.getHoraInicioInt() + " | la hora minimo de esta fila es " + horaMin);
				if(h.getHoraInicioInt()==horaMin || h.getHoraFinInt()== horaMin+100 || (h.getHoraInicioInt()<horaMin && h.getHoraFinInt()>horaMin+100)) {
					//System.out.println("I am in " + horaMin + " en el dia " + h.getDia());
					switch (h.getDia()) {
					case "Lunes":
						horariositems.get(i).setLunes(h.getCurso());
						horariositems.get(i).setLunesProfesor(h.getProfesor());
						horariositems.get(i).setLunesLink(h.getLinkToClass());
						break;
					case "Martes":
						horariositems.get(i).setMartes(h.getCurso());
						horariositems.get(i).setMartesProfesor(h.getProfesor());
						horariositems.get(i).setMartesLink(h.getLinkToClass());
						break;
					case "Miercoles":
						horariositems.get(i).setMiercoles(h.getCurso());
						horariositems.get(i).setMiercolesProfesor(h.getProfesor());
						horariositems.get(i).setMiercolesLink(h.getLinkToClass());
						break;
					case "Jueves":
						horariositems.get(i).setJueves(h.getCurso());
						horariositems.get(i).setJuevesProfesor(h.getProfesor());
						horariositems.get(i).setJuevesLink(h.getLinkToClass());
						break;
					case "Viernes":
						horariositems.get(i).setViernes(h.getCurso());
						horariositems.get(i).setViernesProfesor(h.getProfesor());
						horariositems.get(i).setViernesLink(h.getLinkToClass());
						break;
					}
				}
				horaMin+=100;
			}
			horaMin=horaMax - (nFilas*100);
		}
		
		java.util.Date tfin= new java.util.Date();
		//System.out.println("Hora fin: " + tfin.getTime() + " en milisegundos");
		
		//System.out.println("Diferencia de milisegundos: " + (tfin.getTime() - t.getTime()));
		return horariositems;
	}

	public List<HorarioItemViewModel> GenerateHorarioDeprecated(List<HorarioViewModel> horarios) {
		java.util.Date t= new java.util.Date();
		//System.out.println("Hora inicio: " + t.getTime() + " en milisegundos");
		
		///LISTA DE FILAS PARA EL HORARIO
		List<HorarioItemViewModel> horariositems = new ArrayList<HorarioItemViewModel>();

		HorarioItemViewModel f1 = new HorarioItemViewModel();
		f1.setHora("7:00 - 8:00 am");

		HorarioItemViewModel f2 = new HorarioItemViewModel();
		f2.setHora("8:00 - 9:00 am");

		HorarioItemViewModel f3 = new HorarioItemViewModel();
		f3.setHora("9:00 - 10:00 am");

		HorarioItemViewModel f4 = new HorarioItemViewModel();
		f4.setHora("10:00 - 11:00 am");

		HorarioItemViewModel f5 = new HorarioItemViewModel();
		f5.setHora("11:00 - 12:00 am");

		HorarioItemViewModel f6 = new HorarioItemViewModel();
		f6.setHora("12:00 - 13:00 pm");

		HorarioItemViewModel f7 = new HorarioItemViewModel();
		f7.setHora("13:00 - 14:00 pm");

		HorarioItemViewModel f8 = new HorarioItemViewModel();
		f8.setHora("14:00 - 15:00 pm");

		HorarioItemViewModel f9 = new HorarioItemViewModel();
		f9.setHora("15:00 - 16:00 pm");

		HorarioItemViewModel f10 = new HorarioItemViewModel();
		f10.setHora("16:00 - 17:00 pm");

		HorarioItemViewModel f11 = new HorarioItemViewModel();
		f11.setHora("17:00 - 18:00 pm");

		
		//RECORRO LOS HORARIOS Y LOS ASIGNO A CADA FILA
		for (HorarioViewModel h : horarios) {

			// FORMA ALL IF, CADA HORARIO ES EVALUADO EN CADA IF PARA COLOCAR EN FILAS LOS
			// CURSOS MAYORES A UNA HORA
			if (h.getHoraInicioInt() == 700 || h.getHoraFinInt() == 800) {// RANGO 7-8
				// FALTA DETERMINAR CUANTAS HORAS DURA ESTE CURSO Y ASIGNAR LAS CASILLAS
				// RESPECTIVAS
				switch (h.getDia()) {
				case "Lunes":
					f1.setLunes(h.getCurso());
					f1.setLunesProfesor(h.getProfesor());
					f1.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f1.setMartes(h.getCurso());
					f1.setMartesProfesor(h.getProfesor());
					f1.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f1.setMiercoles(h.getCurso());
					f1.setMiercolesProfesor(h.getProfesor());
					f1.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f1.setJueves(h.getCurso());
					f1.setJuevesProfesor(h.getProfesor());
					f1.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f1.setViernes(h.getCurso());
					f1.setViernesProfesor(h.getProfesor());
					f1.setViernesLink(h.getLinkToClass());
					break;
				}

			}
			if (h.getHoraInicioInt() == 800 || h.getHoraFinInt() == 900 || (h.getHoraInicioInt()<800 && h.getHoraFinInt()>900)) {// RANGO 8-9         la hora inicio es 7 y la hora fin es 10
				switch (h.getDia()) {
				case "Lunes":
					f2.setLunes(h.getCurso());
					f2.setLunesProfesor(h.getProfesor());
					f2.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f2.setMartes(h.getCurso());
					f2.setMartesProfesor(h.getProfesor());
					f2.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f2.setMiercoles(h.getCurso());
					f2.setMiercolesProfesor(h.getProfesor());
					f2.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f2.setJueves(h.getCurso());
					f2.setJuevesProfesor(h.getProfesor());
					f2.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f2.setViernes(h.getCurso());
					f2.setViernesProfesor(h.getProfesor());
					f2.setViernesLink(h.getLinkToClass());
					break;
				}
			}
			if (h.getHoraInicioInt() == 900 || h.getHoraFinInt() == 1000 || (h.getHoraInicioInt()<900 && h.getHoraFinInt()>1000)) {// RANGO 9-10

				switch (h.getDia()) {
				case "Lunes":
					f3.setLunes(h.getCurso());
					f3.setLunesProfesor(h.getProfesor());
					f3.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f3.setMartes(h.getCurso());
					f3.setMartesProfesor(h.getProfesor());
					f3.setMartesLink(h.getLinkToClass());
	
					break;
				case "Miercoles":
					f3.setMiercoles(h.getCurso());
					f3.setMiercolesProfesor(h.getProfesor());
					f3.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f3.setJueves(h.getCurso());
					f3.setJuevesProfesor(h.getProfesor());
					f3.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f3.setViernes(h.getCurso());
					f3.setViernesProfesor(h.getProfesor());
					f3.setViernesLink(h.getLinkToClass());
					break;
				}
			}

			if (h.getHoraInicioInt() == 1000 || h.getHoraFinInt() == 1100 || (h.getHoraInicioInt()<1000 && h.getHoraFinInt()>1100)) {// RANGO 10-11
				switch (h.getDia()) {
				case "Lunes":
					f4.setLunes(h.getCurso());
					f4.setLunesProfesor(h.getProfesor());
					f4.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f4.setMartes(h.getCurso());
					f4.setMartesProfesor(h.getProfesor());
					f4.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f4.setMiercoles(h.getCurso());
					f4.setMiercolesProfesor(h.getProfesor());
					f4.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f4.setJueves(h.getCurso());
					f4.setJuevesProfesor(h.getProfesor());
					f4.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f4.setViernes(h.getCurso());
					f4.setViernesProfesor(h.getProfesor());
					f4.setViernesLink(h.getLinkToClass());
					break;
				}
			}

			if (h.getHoraInicioInt() == 1100 || h.getHoraFinInt() == 1200 || (h.getHoraInicioInt()<1100 && h.getHoraFinInt()>1200)) {// RANGO 11-12

				switch (h.getDia()) {
				case "Lunes":
					f5.setLunes(h.getCurso());
					f5.setLunesProfesor(h.getProfesor());
					f5.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f5.setMartes(h.getCurso());
					f5.setMartesProfesor(h.getProfesor());
					f5.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f5.setMiercoles(h.getCurso());
					f5.setMiercolesProfesor(h.getProfesor());
					f5.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f5.setJueves(h.getCurso());
					f5.setJuevesProfesor(h.getProfesor());
					f5.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f5.setViernes(h.getCurso());
					f5.setViernesProfesor(h.getProfesor());
					f5.setViernesLink(h.getLinkToClass());
					break;
				}
			}
			
			if (h.getHoraInicioInt() == 1200 || h.getHoraFinInt() == 1300 || (h.getHoraInicioInt()<1200 && h.getHoraFinInt()>1300)) {// RANGO 12-13

				switch (h.getDia()) {
				case "Lunes":
					f6.setLunes(h.getCurso());
					f6.setLunesProfesor(h.getProfesor());
					f6.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f6.setMartes(h.getCurso());
					f6.setMartesProfesor(h.getProfesor());
					f6.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f6.setMiercoles(h.getCurso());
					f6.setMiercolesProfesor(h.getProfesor());
					f6.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f6.setJueves(h.getCurso());
					f6.setJuevesProfesor(h.getProfesor());
					f6.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f6.setViernes(h.getCurso());
					f6.setViernesProfesor(h.getProfesor());
					f6.setViernesLink(h.getLinkToClass());
					break;
				}
			}
			
			if (h.getHoraInicioInt() == 1300 || h.getHoraFinInt() == 1400 || (h.getHoraInicioInt()<1300 && h.getHoraFinInt()>1400)) {// RANGO 13-14

				switch (h.getDia()) {
				case "Lunes":
					f7.setLunes(h.getCurso());
					f7.setLunesProfesor(h.getProfesor());
					f7.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f7.setMartes(h.getCurso());
					f7.setMartesProfesor(h.getProfesor());
					f7.setMartesLink(h.getLinkToClass());
					break;
				case "Miercoles":
					f7.setMiercoles(h.getCurso());
					f7.setMiercolesProfesor(h.getProfesor());
					f7.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f7.setJueves(h.getCurso());
					f7.setJuevesProfesor(h.getProfesor());
					f7.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f7.setViernes(h.getCurso());
					f7.setViernesProfesor(h.getProfesor());
					f7.setViernesLink(h.getLinkToClass());
					break;
				}
			}
			
			if (h.getHoraInicioInt() == 1400 || h.getHoraFinInt() == 1500 || (h.getHoraInicioInt()<1400 && h.getHoraFinInt()>1500)) {// RANGO 14-15
				switch (h.getDia()) {
				case "Lunes":
					f8.setLunes(h.getCurso());
					f8.setLunesProfesor(h.getProfesor());
					f8.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f8.setMartes(h.getCurso());
					f8.setMartesProfesor(h.getProfesor());
					f8.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f8.setMiercoles(h.getCurso());
					f8.setMiercolesProfesor(h.getProfesor());
					f8.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f8.setJueves(h.getCurso());
					f8.setJuevesProfesor(h.getProfesor());
					f8.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f8.setViernes(h.getCurso());
					f8.setViernesProfesor(h.getProfesor());
					f8.setViernesLink(h.getLinkToClass());
					break;
				}
			}
			
			if (h.getHoraInicioInt() == 1500 || h.getHoraFinInt() == 1600 || (h.getHoraInicioInt()<1500 && h.getHoraFinInt()>1600)) {// RANGO 15-16

				switch (h.getDia()) {
				case "Lunes":
					f9.setLunes(h.getCurso());
					f9.setLunesProfesor(h.getProfesor());
					f9.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f9.setMartes(h.getCurso());
					f9.setMartesProfesor(h.getProfesor());
					f9.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f9.setMiercoles(h.getCurso());
					f9.setMiercolesProfesor(h.getProfesor());
					f9.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f9.setJueves(h.getCurso());
					f9.setJuevesProfesor(h.getProfesor());
					f9.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f9.setViernes(h.getCurso());
					f9.setViernesProfesor(h.getProfesor());
					f9.setViernesLink(h.getLinkToClass());
					break;
				}
			}
			
			if (h.getHoraInicioInt() == 1600 || h.getHoraFinInt() == 1700 || (h.getHoraInicioInt()<1600 && h.getHoraFinInt()>1700)) {// RANGO 16-17

				switch (h.getDia()) {
				case "Lunes":
					f10.setLunes(h.getCurso());
					f10.setLunesProfesor(h.getProfesor());
					f10.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f10.setMartes(h.getCurso());
					f10.setMartesProfesor(h.getProfesor());
					f10.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f10.setMiercoles(h.getCurso());
					f10.setMiercolesProfesor(h.getProfesor());
					f10.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f10.setJueves(h.getCurso());
					f10.setJuevesProfesor(h.getProfesor());
					f10.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f10.setViernes(h.getCurso());
					f10.setViernesProfesor(h.getProfesor());
					f10.setViernesLink(h.getLinkToClass());
					break;
				}
			}
			
			if (h.getHoraInicioInt() == 1700 || h.getHoraFinInt() == 1800 || (h.getHoraInicioInt()<1700 && h.getHoraFinInt()>1800)) {// RANGO 17-18

				switch (h.getDia()) {
				case "Lunes":
					f11.setLunes(h.getCurso());
					f11.setLunesProfesor(h.getProfesor());
					f11.setLunesLink(h.getLinkToClass());

					break;
				case "Martes":
					f11.setMartes(h.getCurso());
					f11.setMartesProfesor(h.getProfesor());
					f11.setMartesLink(h.getLinkToClass());

					break;
				case "Miercoles":
					f11.setMiercoles(h.getCurso());
					f11.setMiercolesProfesor(h.getProfesor());
					f11.setMiercolesLink(h.getLinkToClass());
					break;
				case "Jueves":
					f11.setJueves(h.getCurso());
					f11.setJuevesProfesor(h.getProfesor());
					f11.setJuevesLink(h.getLinkToClass());
					break;
				case "Viernes":
					f11.setViernes(h.getCurso());
					f11.setViernesProfesor(h.getProfesor());
					f11.setViernesLink(h.getLinkToClass());
					break;
				}
			}
		}

		
		horariositems.add(f1);
		horariositems.add(f2);
		horariositems.add(f3);
		horariositems.add(f4);
		horariositems.add(f5);
		horariositems.add(f6);
		horariositems.add(f7);
		horariositems.add(f8);
		horariositems.add(f9);
		horariositems.add(f10);
		horariositems.add(f11);
		
		
		java.util.Date tfin= new java.util.Date();
		//System.out.println("Hora fin: " + tfin.getTime() + " en milisegundos");
		
		//System.out.println("Diferencia de milisegundos: " + (tfin.getTime() - t.getTime()));

		return horariositems;
	}
	
	

}
