window.addEventListener("load", () => {
    const url = new URL(window.location.href);
    const err = url.searchParams.get("err");
    if (err != null) {
        alert(err)
    }
})
