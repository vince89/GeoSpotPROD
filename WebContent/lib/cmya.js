function cmyk_to_rgb2($c, $m, $y, $k)
{
    $c = (255 * $c) / 100;
    $m = (255 * $m) / 100;
    $y = (255 * $y) / 100;
    $k = (255 * $k) / 100;

    $r = Math.round(((255 - $c) * (255 - $k)) / 255) ;
    $g = Math.round((255 - $m) * (255 - $k) / 255) ;
    $b = Math.round((255 - $y) * (255 - $k) / 255) ; 
    $o = {};
    $o.r = $r ;
    $o.g = $g ;
    $o.b = $b ;

    return $o ;
}

console.log(cmyk_to_rgb2(70,17,1,0));