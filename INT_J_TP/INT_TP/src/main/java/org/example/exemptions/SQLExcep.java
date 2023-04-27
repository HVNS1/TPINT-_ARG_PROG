package org.example.exemptions;

import java.sql.SQLException;

public class SQLExcep extends SQLException {
    public SQLExcep(String msg){
        super(msg);
    }
}
