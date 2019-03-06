//开始gogo
//设置开始时间
var startHour = 0;
var startMinute = 0;
var startSecond = 0;
var startMillionSeconds = 0;
var result;
// function predator() {
//     // 抢券
//     var element = document.getElementsByClassName("coupon     pic_errorPlace  coming")[0];
//     var myEvent = document.createEvent('MouseEvents');
//     myEvent.initMouseEvent('click', true, true, window, 1, 12, 345, 7, 220, false, false, true, false, 0, null);
//     element.dispatchEvent(myEvent);

// }

function setTime(hour, minute, second, millionSeconds) {
    startHour = hour;
    startMinute = minute;
    startSecond = second;
    startMillionSeconds = millionSeconds;

    //result = setInterval(gogo, 20);
    result = setInterval(openURL, 20);
}

// function gogo() {
//     var date = new Date();
//     var nowHour = date.getHours();
//     var nowMinute = date.getMinutes();
//     var nowSecond = date.getSeconds();
//     var nowMillionSeconds = date.getMilliseconds();
//     if (nowHour == startHour && nowMinute == startMinute && nowSecond == startSecond && nowMillionSeconds >= startMillionSeconds) {
//         clearInterval(result);
//         var times = 66;
//         while(times > 0) {
//             setTimeout(predator, 10);
//             times --;
//         }
        
//     }
// }

function openURL() {
    
    var date = new Date();
    var nowHour = date.getHours();
    var nowMinute = date.getMinutes();
    var nowSecond = date.getSeconds();
    var nowMillionSeconds = date.getMilliseconds();
    if (nowHour >= startHour && nowMinute >= startMinute && nowSecond >= startSecond && nowMillionSeconds >= startMillionSeconds) {
        clearInterval(result);
        window.open("//coupon.jd.com/ilink/couponActiveFront/front_index.action?key=170bc66d353a433d92d0a42125681faa&roleId=14894388&to=sale.jd.com/act/guXmKoHwOfd2ZT.html&");
    }
    

}
