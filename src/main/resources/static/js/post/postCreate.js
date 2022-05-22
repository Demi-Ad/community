const tagInput = document.querySelector("#tagInput")
const tagList = document.querySelector("#tagList")
const tagArea = document.querySelector("#tagShow")

window.addEventListener("load",() => {1
    if (tagList.value !== '') {
        tagList.value = tagList.value.split(" ").map(value => value.slice(1,value.length)).join(" ")
    }
})


function createHtml(tagStrList) {
    while (tagArea.hasChildNodes()) {
        tagArea.removeChild(tagArea.firstChild)
    }

    if (tagStrList.length === 1 && tagStrList[0] === '')
        return

    let tagElemList = []

    tagStrList.forEach(tag => {

        const spanOuter = document.createElement("span")
        const spanInner = document.createElement("span")

        spanInner.innerText = "#" + tag
        spanInner.className = "fs-5 tag"
        const spanInnerDeleteBtn = document.createElement("span")
        spanInnerDeleteBtn.innerHTML = "<i class=\"bi bi-x\"></i>"
        spanInnerDeleteBtn.className = "ms-2 text-black fs-5"
        spanInnerDeleteBtn.addEventListener("click",() => {
            tagList.value = tagList.value.split(" ").filter(str => !(str === tag)).join(" ")
        })

        spanOuter.appendChild(spanInner)
        spanOuter.appendChild(spanInnerDeleteBtn)
        spanOuter.className = "me-2 bg-info rounded-3 text-white bg-opacity-50 me-1 ps-2 pe-2"

        tagElemList.push(spanOuter)
    })

    return tagElemList
}


let observer = new MutationObserver(e => {
    const split = tagList.value.split(" ");
    tagArea.append(...createHtml(split))
});

observer.observe(tagList,{
    attributes: true
})

tagInput.addEventListener("keydown",e => {
    if (e.code === "Enter") {
        e.stopPropagation()
        e.preventDefault()
        const tag = e.target.value
        if (tag === '') {
            alert("공백은 불가능")
            return;
        }
        if (tagList.value.split(" ").length >= 5) {
            alert("태그는 5개 까지 입니다")
            e.target.value = ''
            return;
        }

        const tagListValue = tagList.value.split(" ")
        const isInclude = tagListValue.includes(tag);

        if (isInclude) {
            alert("이미 존재하는값")
            return
        }

        tagList.value === '' ?  tagList.value += `${tag}` : tagList.value += ` ${tag}`
        tagInput.value = ''
    }
})