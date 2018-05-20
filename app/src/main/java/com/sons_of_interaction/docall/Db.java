package com.sons_of_interaction.docall;

import java.util.ArrayList;

public class Db{

    private ArrayList<Person> patientsOfDocPodda= new ArrayList<>();
    private ArrayList<Person> patientsOfDocAgresti = new ArrayList<>();
    private ArrayList<Doctor> listOfDoctors = new ArrayList<>();
    private ArrayList<Visit> visits = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();

    public Db(){
        initialize();
    }

    public void initialize() {

        Doctor docPodda= new Doctor("Salvatore","Podda","PDDSVT64C06I452W","1984-11-04","archimede");
        Doctor docAgresti= new Doctor("Giuseppe","Agresti","AGRGSP68C06I552W","1978-05-01","leiene");

        Person marioRossi = new Person("Mario","Rossi","1996-02-17","rossice","RSSMRA80A01B354W",docPodda);
        Person luciaMancini = new Person("Lucia","Mancini","1965-09-06","pratosardo","MNCLCU85P50M082B",docPodda);
        Person robertoCossu = new Person("Roberto","Cossu","1975-05-01","forzacagliari","CSSRRT58T22G113F",docAgresti);
        Person kevinAgus = new Person("Kevin","Agus","1996-08-17","forzainter","GSAKVN96M17I851G",docPodda);

        Visit visit1 = new Visit("RSSMRA80A01B354W","Febbre alta","PDDSVT64C06I452W","2018-01-09","Via Roma,190,09123 Cagliari CA, Italia");
        Visit visit2 = new Visit("CSSRRT58T22G113F","Dolori al costato","AGRGSP68C06I552W","2018-01-25","Via Liguria,9,09034 Villasor CA, Italia");
        Visit visit3 = new Visit("RSSMRA80A01B354W","Dolore ai denti","PDDSVT64C06I452W","2017-04-09","Via Umberto I,76,09016 Iglesias CI, Italia");
        Visit visit4 = new Visit("CSSRRT58T22G113F","Forte emicrania","AGRGSP68C06I552W","2017-09-06","Via Cabras,97,09170 Oristano OR, Italia");

        Patient marioBianchi = new Patient("Mario","Rossi","1996-02-17","rossice","RSSMRA80A01B354W",false);
        Patient luciaNeri = new Patient("Lucia","Mancini","1965-09-06","pratosardo","MNCLCU85P50M082B",false);
        Patient kevinsAgus = new Patient("Kevin","Agus","1996-08-17","forzainter","GSAKVN96M17I851G",false);

        patients.add(marioBianchi);
        patients.add(luciaNeri);
        patients.add(kevinsAgus);

        visits.add(visit1);
        visits.add(visit2);
        visits.add(visit3);
        visits.add(visit4);

        patientsOfDocPodda.add(kevinAgus);
        patientsOfDocPodda.add(marioRossi);
        patientsOfDocPodda.add(luciaMancini);

        patientsOfDocAgresti.add(robertoCossu);

        listOfDoctors.add(docAgresti);
        listOfDoctors.add(docPodda);

    }

    public ArrayList<Patient> patients(){
        return patients;
    }

    public ArrayList<Person> poddasPatients(){
        return patientsOfDocPodda;
    }

    public ArrayList<Person> agrestisPatients(){
        return patientsOfDocAgresti;
    }

    public ArrayList<Doctor> getListOfDoctors() {return listOfDoctors;}

    public ArrayList<Visit> getListOfVisits(String fiscalCode) {

        ArrayList<Visit> visitsPerFiscalCode = new ArrayList<>();

        for(Visit v: visits){
            if(v.getFcPatient().equalsIgnoreCase(fiscalCode)){
                visitsPerFiscalCode.add(v);
            }
        }
        return visitsPerFiscalCode;
    }

    public void addVisit(Visit v){
        visits.add(0,v);
    }

    public void updateVisits(ArrayList<Visit> newVisits){
        this.visits=newVisits;
    };

    public ArrayList<Person> getListOfPersons() {

        ArrayList<Person> personArrayList = new ArrayList<>();

        for(Person p: patientsOfDocPodda){
            personArrayList.add(p);
        }
        return personArrayList;
    }

}