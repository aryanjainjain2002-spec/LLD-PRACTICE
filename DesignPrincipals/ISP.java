//INTERFACE SEGREGATION PRINCIPAL

//Fat interface leads to violatioin of this principal

//Clients should not be forced to depend on methods they do not use.

//Eg. MediaPlayer -- everything audio & video - 7 methods
// needs to implement all most of them are empty or throw an error.

//segregate them AudioPlayControls , VideoPlayControls -> implement them

//focus on the clients need then design interfaces


//group methods that are associated with common responsibalties
public class ISP {
    
}
