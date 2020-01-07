function session(){
    (new ldLoader({root: ".ldld.full"})).on();
}

function setFooterColor(bgColor){
    var tDiv = document.getElementById('ftr');

    bgColor = bgColor.replace(/[^\d,]/g, '').split(',');
    var hsl = RGBToHSL(bgColor[0],bgColor[1],bgColor[2]);
    var colors=createMatchingColor(hsl, 180, 0, 0);

    tDiv.style.color=colors.text;
    tDiv.style.backgroundColor=bgColor;
    //tDiv.style.textShadow='1px 2px 5px  '+colors.shadow;
}

function createMatchingColor(color){
    var arr=color.match(/([0-9]+)/g);
    var h=arr[0],s=arr[1],l=arr[2];
    /*originals*/
    var o_h=h, o_s=s, o_l=l;
    s=100;
    if(o_s<=25){
        if(o_l>60){
            l=10;
        } else {
            l=95;
        }
    } else {
        if((o_h>=25 && o_h<=195) || o_h>=295){
            l=10;
        } else if(
            (o_h>=285 && o_h<295) ||
            (o_h>195 && o_h<=205)
        ){
            h=60;
            l=50;
        } else {
            l=95;
        }
    }
    if(
        (o_h>=295 ||
            (o_h>20 && o_h<200)) && o_l<=35
    ){
        l=95;
    } else if((
        (o_h<25 ||o_h>275) && o_l>=60) ||
        (o_h>195 && o_l>=70)
    ){
        l=10;
    }
    /*shadow*/
    if(l<25){
        s_l=80;
    } else {
        s_l=10;
    }
    if(h===60 && (l<90 || l>20)){
        s_h=320;
    } else {
        s_h=h;
    }
    s_s=o_s;
    return {text: createHSL(h,s,l), shadow: createHSL(s_h,s_s,s_l)};
}

function createHSL(h, s, l){
    return 'hsl('+h+','+s+'%, '+l+'%)';
}

/**
 * @return {string}
 */
function RGBToHSL(r,g,b) {
    // Make r, g, and b fractions of 1
    r /= 255;
    g /= 255;
    b /= 255;

    // Find greatest and smallest channel values
    cmin = Math.min(r,g,b),
        cmax = Math.max(r,g,b),
        delta = cmax - cmin,
        h = 0,
        s = 0,
        l = 0;

    // Calculate hue
    // No difference
    if (delta === 0)
        h = 0;
    // Red is max
    else if (cmax === r)
        h = ((g - b) / delta) % 6;
    // Green is max
    else if (cmax === g)
        h = (b - r) / delta + 2;
    // Blue is max
    else
        h = (r - g) / delta + 4;

    h = Math.round(h * 60);

    // Make negative hues positive behind 360°
    if (h < 0)
        h += 360;

    // Calculate lightness
    l = (cmax + cmin) / 2;

    // Calculate saturation
    s = delta === 0 ? 0 : delta / (1 - Math.abs(2 * l - 1));

    // Multiply l and s by 100
    s = +(s * 100).toFixed(1);
    l = +(l * 100).toFixed(1);

    return "hsl(" + h + "," + s + "%," + l + "%)";
}