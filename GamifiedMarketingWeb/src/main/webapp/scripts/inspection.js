const getToday = () => {
    const dtToday = new Date();

    let month = dtToday.getMonth() + 1;
    let day = dtToday.getDate();
    const year = dtToday.getFullYear();

    if (month < 10)
        month = '0' + month.toString();
    if (day < 10)
        day = '0' + day.toString();

    return year + "-" + month + "-" + day
}

const changeDate = () => {
    const date = document.getElementById("day").value
    location.href = "/admin/inspection?day=" + date + ""
}

window.onload = () => document.getElementById("day").setAttribute("max", getToday())

