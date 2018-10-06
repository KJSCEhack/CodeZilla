const functions = require("firebase-functions")
const express = require("express")
const app = express()
const admin = require('firebase-admin');
admin.initializeApp();

const db = require('./services/database');

let addWard = functions.database.ref('/reports/')
    .onCreate((snapshot, context) => {
      const original = snapshot.val();
      let location = [original.mLatitude, original.mLongitude];
      return db
                .getWards(location)
                .then((wardList) => {
                    original.ward = wardList[0].name;
                    return snapshot.ref.set(original);
                })
                .catch(console.log)
    });
const api = functions.https.onRequest(app)


module.exports = {
  api
}