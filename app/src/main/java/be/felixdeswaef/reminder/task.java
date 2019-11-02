package be.felixdeswaef.reminder;

import java.sql.Time;
import java.sql.Timestamp;

public class task{
    public int id;
    public String name;
    public Timestamp deadline;
    public Timestamp creationdate;
    public Boolean checked;
    public Boolean hidden;
    public String description;
    public int completion;

    public task(String nname){
        id = -1;
        name = nname;

    }


}
