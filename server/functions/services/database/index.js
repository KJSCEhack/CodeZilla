var admin = require("firebase-admin");
var db = admin.database();

function getWards(location) {
    return new Promise((resolve, reject) => {
        var ref = db.ref("wards");
        ref.on("value", function (snapshot) {
            let wardsList = snapshot.val();
            let distList = [];
            wardsList.forEach((ward,i) => {
                distList.push([dist(ward.location.lat, ward.location.lng, location[0], location[1]),i])
            });
            distList.sort();
            resolve([
                wardsList[distList[0][1]],
                wardsList[distList[1][1]],
                wardsList[distList[2][1]]
            ]);
        }, function (errorObject) {
            reject(errorObject);
        });
    })
}







function dist(lat1, lon1, lat2, lon2) {
    var R = 6371; // km
    var dLat = toRad(lat2 - lat1);
    var dLon = toRad(lon2 - lon1);
    var lat1 = toRad(lat1);
    var lat2 = toRad(lat2);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c;
    return d;
}

function toRad(deg) {
    return deg * Math.PI / 180;
}



module.exports = {
    getWards
}