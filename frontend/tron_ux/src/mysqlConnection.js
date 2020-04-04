function mysqlConnection(){
    this.mysql = require('mysql');
    this.param = {
        host: "localhost",
        user: "tron",
        password: "tron",
        database: "tron_light_cycles"
    };
}

mysqlConnection.prototype.getAllRiders = function(callback){
    con = this.mysql.createConnection(this.param);
    con.connect(function(err){
        if(err) throw err;
        con.query("SELECT * FROM rider;", function(err, result){
            if(err) throw err;
            con.end();
            return callback(result);
        });
    });
};

mysqlConnection.prototype.getRiderByUsername = function(username, callback){
    con = this.mysql.createConnection(this.param);
    con.connect(function(err){
        if(err) throw err;
        con.query("SELECT * FROM rider WHERE username = ?;", [username], function(err, result){
            if(err) throw err;
            con.end();
            return callback(result);
        });
    });
};

mysqlConnection.prototype.addRider = function(username, firstname, name, password, email, cycle_color, ranking, callback){
    con = this.mysql.createConnection(this.param);
    con.connect(function(err){
        if(err) throw err;
        con.query("INSERT INTO rider (username, firstname, name, password, email, cycle_color, ranking) VALUES (?,?,?,?,?,?,?)", [username, firstname, name, password, email, cycle_color, ranking], function(err, result){
            if(err) throw err;
            con.end();
            return callback(result);
        });
    });
};




/*
    How to use this shite ...

    var mysqlCon = new mysqlConnection();
    mysqlCon.getRiders(function(result){
        console.log(result);
    });
*/