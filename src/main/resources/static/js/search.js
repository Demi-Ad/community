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


const searchInputElement = document.querySelector("#search_input");
const resultFormElement = document.querySelector("#search_result_form");


searchInputElement.addEventListener("input", elem => {

    if(window.debounceing) {
        clearTimeout(window.debounceing);
    }
    window.debounceing = setTimeout(function() {

        if (elem.target.value.length < 0 || elem.target.value.replaceAll(" ", "").length === 0) {
            resultFormElement.style.display = "none";
            return;
        }

        const searchParam = document.querySelector("#search_param").value;

        const params = {
            "param": searchParam,
            "keyword": elem.target.value
        }

        const query = Object.keys(params)
            .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k])).join('&');


        fetch(`/search/preview?${query}`, {
            method: "GET"
        }).then(res => {
            if (res && res.status === 200) {
                return res.json()
            }
        }).then(json => {
            if (json.previewDtoList.length === 0) {
                resultFormElement.style.display = "none";
                return;
            }
            const result = document.querySelector("#search_result")
            result.innerHTML = json.previewDtoList
                .map(dto => `
                        <div class="d-flex ms-3">
                            <img src="${dto.img}" class="m-2 rounded-circle" style="width: 25px;height: 25px" >
                            <a href="${dto.link}" class="p-normal m-2">${dto.item}</a>
                        </div>`)
                .join("");
            resultFormElement.style.display = "";
        })
    }, 500);
})
