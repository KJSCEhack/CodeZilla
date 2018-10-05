const axios = require('axios');

var config = {
    apiKey: "AIzaSyD8YJqS2wA02tIuygUGghVp5WeEh3qDggI",
    authDomain: "voices-mumbai.firebaseapp.com",
    databaseURL: "https://voices-mumbai.firebaseio.com",
    storageBucket: "voices-mumbai.appspot.com"
};
firebase.initializeApp(config);

// Get a reference to the database service
var database = firebase.database();

//Get total number of reports
function getTotalReports() {
    var solvedCount = 0, remainingCount = 0, ongoingCount = 0;
    var reportsRef = firebase.database().ref('reports');
    reportsRef.on('value', function (snapshot) {
        snapshot = snapshot.val();
        snapshot.forEach((report) => {
            if (report.status === -1)
                solvedCount++;
            else if (report.status === 0)
                remainingCount++;
            else
                ongoingCount++;
        });
    });

    
}



