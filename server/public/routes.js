var url = "http://localhost:5001/voices-mumbai/us-central1/api/"

window.onload = function () {

        getTotalUsers();
        getTotalReports();
        getReports();
    

}

function getTotalUsers() {
    let users = 0;

    axios
        .get(url + 'users')
        .then((response) => {
            let data = response.data;
            users = data['userCount'];
            document.getElementById("totalUsers").innerHTML = users;
        })
        .catch((err) => console.log(err));
}

function getTotalReports() {
    let totalReports = 0, solved = 0, remaining = 0;

    axios
        .get(url + 'reports/count')
        .then((response) => {
            let data = response.data;
            totalReports = data['total'];
            solved = data['solved'];
            remaining = data['remaining'];

            console.log(totalReports, solved, remaining);

            document.getElementById("totalReports").innerHTML = totalReports;
            document.getElementById("resolved").innerHTML = solved;
            document.getElementById("remaining").innerHTML = remaining;
        })
        .catch((err) => console.log(err));
}

function getReports() {
    axios
        .get(url + 'reports')
        .then((response) => {
            reports = JSON.parse(response.data);

            generateTable(reports);
        })
        .catch((err) => console.log)
}

function getReport(uid)  {
    axios
        .get(`${url}report?uid=${uid}`)
        .then((response) => {
            let data = JSON.parse(response.data);

            document.getElementById("category").innerHTML = data.category;
            document.getElementById("upvotes").innerHTML = data.upvotes;
        });
}

function generateTable(data) {

    data.forEach((report) => {
        let str = `<tr>
        <th scope="row">
        <a href="./examples/profile.html?uid=${report.uid}">${report.category}</a>
        </th>
        <td id="upvotes">
          ${report.upvotes}
        </td>
      </tr>`;

      $("tbody").append(str);
    })
}
