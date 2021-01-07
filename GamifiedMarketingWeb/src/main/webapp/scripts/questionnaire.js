const checkEmptyFields = () => {

    let check = false

    document
        .getElementById("marketingSection")
        .childNodes
        .forEach(item => {
            item.childNodes.forEach(
                subItem => {

                    if (subItem.nodeName === "TEXTAREA") {
                        console.log(subItem.value)
                    }

                    if (subItem.nodeName === "TEXTAREA" && subItem.value === "") {
                        check = true
                    }
                }
            )
        })

    return check
}


const goToStatisticalSection = () => {

    if (checkEmptyFields()) {
        alert("Answer all the questions before proceeding")
        return
    }

    const statisticalSection = document.getElementById("statisticalSection")
    const marketingSection = document.getElementById("marketingSection")

    marketingSection.style.visibility = "hidden"
    marketingSection.style.position = "absolute"

    statisticalSection.style.visibility = "visible"
    statisticalSection.style.position = "static"

}

const goToMarketingSection = () => {
    const statisticalSection = document.getElementById("statisticalSection")
    const marketingSection = document.getElementById("marketingSection")

    marketingSection.style.visibility = "visible"
    marketingSection.style.position = "static"

    statisticalSection.style.visibility = "hidden"
    statisticalSection.style.position = "absolute"
}


const goToHome = () => window.location.href = "/pday"