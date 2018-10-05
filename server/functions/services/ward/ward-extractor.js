const fs = require('fs');
const axios = require('axios');

let rawData = fs.readFileSync('ward-rw').toString()
rawData = rawData.replace(/(?:\\[r]|[\r]+)+/g, "");

let wardList = rawData.split("\n").filter(str => str!="");
let newList = [];
let a = 0;

for (let i = 0; i <= wardList.length-2; i+=3) {
    axios.get("https://maps.googleapis.com/maps/api/geocode/json",{
        params:{
            address:wardList[i+1].trim(),
            key:"AIzaSyD8YJqS2wA02tIuygUGghVp5WeEh3qDggI"
        }
    })
    .then((res) => {
        res = res.data;
        let data = {
            name:wardList[i].substr(wardList[i].indexOf('d')+1).trim().replace(":",""),
            address:wardList[i+1].trim(),
            contacts:wardList[i+2].substr(wardList[i+2].indexOf(':')+1).trim().split(","),
            location: res.results[0].geometry.location
        }
        newList.push(data);
        a++;
        if(a >= 24) {
            fs.writeFileSync("JSON",JSON.stringify(newList,null,5));
        }
    })
    .catch(console.log);
}




