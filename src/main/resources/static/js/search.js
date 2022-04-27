const search_param = document.getElementById("search_param")
const search_concept = document.getElementById("search_concept")

document.querySelector("input[name='keyword']").addEventListener("Enter", ev => ev.preventDefault())


document.querySelectorAll(".dropdown-item").forEach(elem => {
    elem.addEventListener("click", _ => {
        search_param.value = elem.dataset.value
        const tag_a = document.createElement("a")
        tag_a.innerText = elem.innerText
        search_concept.innerText = elem.innerText
    })
})

