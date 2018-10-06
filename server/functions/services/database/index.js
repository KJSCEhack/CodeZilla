var admin = require("firebase-admin");
var db = admin.database();

function getWards(location) {
    return new Promise((resolve, reject) => {
        var ref = db.ref("wards");
        ref.on("value", function (snapshot) {
            let wardsList = snapshot.val();
            let distList = [];
            wardsList.forEach((ward, i) => {
                distList.push([dist(ward.location.lat, ward.location.lng, location[0], location[1]), i])
            });
            distList.sort();
            resolve(
                wardsList[distList[0][1]]
            );
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

//Get total number of reports
function getTotalReports() {
    var solvedCount = 0, remainingCount = 0, count = 0;

    return new Promise((resolve, reject) => {
        var reportsRef = db.ref('reports');
        reportsRef.on('value', function (snapshot) {
            snapshot = snapshot.val();

            if (snapshot === null) {
                return;
            }
            else {
                let keys = Object.keys(snapshot);
                keys.forEach((report) => {
                    count++;

                    report = snapshot[report];

                    if (report.hasOwnProperty('status')) {
                        if (report.status === 1) {
                            solvedCount++;
                        }
                        else {
                            remainingCount++;
                        }
                    }
                });

                resolve({
                    total: count,
                    solved: solvedCount,
                    remaining: remainingCount,
                });
            }
        }, function (err) {
            reject(err);
        });
    })
}

// Get Total Users
function getTotalUsers() {
    var count = 0;

    return new Promise((resolve, reject) => {
        var usersRef = db.ref('users');
        usersRef.on('value', function (snapshot) {
            snapshot = snapshot.val();

            if (snapshot === null) {
                return;
            }
            else {

                let keys = Object.keys(snapshot);

                keys.forEach((user) => {
                    count++;
                })
                resolve({
                    userCount: count
                });
            }
        }), function (err) {
            reject(err);
        }
    })
}

// Get List of Reports
function getReports() {
    var reports = [];

    return new Promise((resolve, reject) => {
        var reportsRef = db.ref('reports');
        reportsRef.on('value', function (snapshot) {
            snapshot = snapshot.val();

            if (snapshot === null) {
                return;
            }
            else {

                let keys = Object.keys(snapshot);

                keys.forEach((report) => {
                    reports.push(snapshot[report]);
                })
                resolve(JSON.stringify(reports));
            }
        }), function (err) {
            reject(err);
        }
    })
}

function getComments(uid) {
    return new Promise((resolve, reject) => {
        var reportsRef = db.ref('reports');

        reportsRef.on('value', function (snapshot) {
            snapshot = snapshot.val();

            if (snapshot === null) {
                return;
            }
            else {

                let keys = Object.keys(snapshot);

                keys.forEach((report) => {
                    if (snapshot[report].uid === uid) {
                        resolve(JSON.stringify(snapshot[report].mComments));
                        return;
                    }
                });
            }
        }), function (err) {
            reject(err);
        }
    })
}

function getReport(uid) {
    return new Promise((resolve, reject) => {
        var reportsRef = db.ref('reports');

        reportsRef.on('value', function (snapshot) {
            snapshot = snapshot.val();

            if (snapshot === null) {
                return;
            }
            else {

                let keys = Object.keys(snapshot);

                keys.forEach((report) => {
                    if (snapshot[report].uid === uid) {
                        resolve(snapshot[report]);
                        return;
                    }
                });
            }
        }), function (err) {
            reject(err);
        }
    })
}

function markAsDone(uid) {
    return new Promise((resolve, reject) => {
        var reportRef = db.ref('reports/' + uid);

        reportRef.update({
            status: 1
        });

        resolve();
    }, function (err) {
        reject(err);
    })
}

module.exports = {
    getWards,
    getTotalReports,
    getTotalUsers,
    getReports,
    getReport,
    getComments,
    markAsDone
}