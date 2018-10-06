const functions = require("firebase-functions")
const express = require("express")
const app = express()
const admin = require('firebase-admin');
admin.initializeApp();

const db = require('./services/database');


app.get("/wards", (req, res) => {

    let lat = parseFloat(req.query.lat);
    let lng = parseFloat(req.query.lng);

    db
        .getWards([lat, lng])
        .then((ward) => {
            res.json(ward);
        })
        .catch(console.log)
})

app.get("/users", (req, res) => {
    db
        .getTotalUsers()
        .then((users) => {
            res.json(users);
        })
        .catch(console.log)
})

app.get("/reports", (req, res) => {
    db
        .getReports()
        .then((reports) => {
            res.json(reports);
        })
        .catch(console.log)
})

app.get("/report", (req, res) => {

    let uid = req.query.uid;

    db
        .getReport(uid)
        .then((report) => {
            res.json(report);
        })
        .catch(console.log)
})

app.get("/report/comments", (req, res) => {

    let uid = req.query.uid;

    db
        .getComments(uid)
        .then((report) => {
            res.json(report);
        })
        .catch(console.log)
})

app.get("/report/mark", (req, res) => {
    
    let uid = req.query.uid;

    db
    .markAsDone(uid)
    .then(() => {
        res.json({
            success : 1
        });
    })
    .catch(console.log)
})

app.get("/reports/count", (req, res) => {
    db
        .getTotalReports()
        .then((reports) => {
            res.json(reports);
        })
        .catch(console.log)
})

const api = functions.https.onRequest(app)
module.exports = {
    api
}