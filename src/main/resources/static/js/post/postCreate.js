let tag = []

window.addEventListener("load",function () {
    document.getElementById("form").addEventListener("submit",function (e) {
        e.preventDefault()
        const form = document.getElementById("form")
        const formData = new FormData(form)
        const tagList = []

        document.querySelectorAll(".tag").forEach(elem => {
            tagList.push(elem.innerText)
        })

        const tagStr = tagList.join(",")

        formData.append("tagJoiningStr",tagStr)
        fetch("",{
            method: "POST",
            body: formData
        }).then(res => {
            if (res.ok) {
                window.location.href = res.url
            } else {
                alert("작성중에 오류가 발생했습니다")
            }
        }).catch(reason => {
            console.log(reason)
        })
    })
})


document.getElementById("tagInput").addEventListener("keypress", function (key) {
    if (key.key === "Enter") {
        const tagInput = document.getElementById("tagInput")

        if (tagInput.value === "")
            return

        const tagList = document.getElementById("tagShow")

        if (tagList.childNodes.length >= 6) {
            alert("태그는 5개까지 가능합니다")
            return;
        }

        const tagItem = tagInput.value;
        if (tag.includes(tagItem)) {
            alert("중복된 태그")
            tagInput.value = ""
            return;
        }
        tag.push(tagItem)
        const spanOuter = document.createElement("span")
        const spanInner = document.createElement("span")

        spanInner.innerText = "#" + tagItem;
        spanInner.className = "fs-5 tag"
        const spanInnerDeleteBtn = document.createElement("span")
        spanInnerDeleteBtn.innerHTML = "<i class=\"bi bi-x\"></i>"
        spanInnerDeleteBtn.className = "ms-2 text-black fs-5"

        spanInnerDeleteBtn.addEventListener("click",function () {
            tagList.removeChild(spanOuter)
            tag = [...tag.filter(value => value !== tagItem)]
        })
        spanOuter.appendChild(spanInner)
        spanOuter.appendChild(spanInnerDeleteBtn)
        spanOuter.className = "me-2 bg-info rounded-3 text-white bg-opacity-50 me-1 ps-2 pe-2"
        tagList.appendChild(spanOuter)

        tagInput.value = ""

        tagInput.focus()
    }
})