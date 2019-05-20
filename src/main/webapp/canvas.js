function navigateToCanvas() {
    user = getAuthorization();
    showContents("canvas")
    console.log(user)
}

function storeSketch(sketch) {
    storeItem('sketch', sketch);
}

function retrieveSketch() {
    return retrieveItem('sketch');
}