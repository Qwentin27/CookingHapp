<?php
$mysqli = new mysqli("localhost", "xxxxxxxxxxx", "xxxxxxxxxxx", "xxxxxxxxxxx"); //Les identifiants ont été masqué
$mysqli->set_charset($mysqli, 'utf8');
if ($mysqli->connect_errno) {
    echo "Echec lors de la connexion à MySQL : (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}
if (isset($_POST["requete"]) && !empty($_POST["requete"])) {
    $res = $mysqli->query($mysqli->real_escape_string($con, $_POST["requete"]));
}
else {
    $res = $mysqli->query("SELECT * from recettes");
    $res->data_seek(0);
    while ($row = $res->fetch_assoc()) {
        $i = 0;
        foreach ($row as $val) {
            $order   = array("\r\n", "\n", "\r");
            $replace = '<br />';
            $v = str_replace($order, $replace, $val);
            if ($i == count($row)-1)//La dernière colonne n'a pas besoin de séparateur
                echo $v;
            elseif($i != 0)//On enlève la colonne des id des recettes
                echo $v . "\u{2016}";
            $i++;
        }
        echo "<br>";
    }
}
$mysqli->close();
?>