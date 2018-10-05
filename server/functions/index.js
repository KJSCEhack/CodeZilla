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
        .getWards([lat,lng])
        .then((wardList) => {
            res.json(wardList);
        })
        .catch(console.log)



})

const api = functions.https.onRequest(app)
module.exports = {
  api
}