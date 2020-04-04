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
        //console.log("Connected!");
        con.query("SELECT * FROM rider;", function(err, result){
            if(err) throw err;
            //console.log("Result: " + result);
            con.end();
            return callback(result);
        });
    });
};

mysqlConnection.prototype.getRiderById = function(id, callback){
    con = this.mysql.createConnection(this.param);
    con.connect(function(err){
        if(err) throw err;
        //console.log("Connected!");
        con.query("SELECT * FROM rider where id=?;", [id], function(err, result){
            if(err) throw err;
            //console.log("Result: " + result);
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
