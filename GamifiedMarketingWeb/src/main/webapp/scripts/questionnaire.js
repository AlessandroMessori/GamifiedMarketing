const goToStatisticalSection = () => {
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