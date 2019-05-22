function navigateToCanvas() {
    user = getAuthorization();
    showContents("canvas")
    console.log(user)
}

function storeSketchId(sketch) {
    storeItem('sketch', sketch);
}

function retrieveSketchId() {
    return retrieveItem('sketch');
}