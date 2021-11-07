$("document").ready(function () {
    setPostponedItemsAmountIcon();
    setCartItemsAmountIcon();
});

function setPostponedItemsAmountIcon() {
    const cookiePostponed = Cookies.get('postponedContents');
    const postponedSlugsAmount = !cookiePostponed ? 0 : cookiePostponed.split("/").length;
    $(".postponedAmount").each(function(){
        $(this).text(postponedSlugsAmount);
    });
}

function setCartItemsAmountIcon() {
    const cookieCart = Cookies.get('cartContents');
    const cartSlugsAmount = !cookieCart ? 0 : cookieCart.split("/").length;
    $("#cartAmount").text(cartSlugsAmount);
}