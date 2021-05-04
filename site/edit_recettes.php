<?php
if(isset($_POST["nom"])) {
    $mysqli = new mysqli("localhost", "xxxxxxxxxxx", "xxxxxxxxxxx", "xxxxxxxxxxx"); //Les identifiants ont été masqué
    $mysqli->set_charset($mysqli, 'utf8');
    if ($mysqli->connect_errno) {
        echo "Echec lors de la connexion à MySQL : (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
    }
    $nom = $_POST["nom"];
    if(isset($_POST["note"]) && isset($_POST["nombre_votants"])) {
        $note = $_POST["note"];
        $nombre_votants=$_POST["nombre_votants"];
        $res = $mysqli->query("UPDATE `recettes` SET note=$note, nombre_votants=$nombre_votants WHERE nom='$nom'");
        if ($res === TRUE) {
            echo "Base de données mise à jour";
        } else {
            echo "Erreur d'insertion: " . $mysqli->error;
        }
    }
    elseif(isset($_POST["type"]) && isset($_POST["ingredients"]) && isset($_POST["instructions"]) && isset($_POST["url_image"]) && isset($_POST['nb_personnes'])) {
        $type = $_POST["type"];
        $nb_personnes = $_POST['nb_personnes'];
        $ingredients = $_POST["ingredients"];
        $instructions = $_POST["instructions"];
        $url_image = $_POST["url_image"];
        $res = $mysqli->query("INSERT INTO recettes (id, nom, type, note, nombre_votants, nb_personnes, ingredients, instructions, url_image) VALUES (0, \"$nom\", \"$type\", 0, 0, \"$nb_personnes\", \"$ingredients\", \"$instructions\", \"$url_image\")");
        if ($res === TRUE) {
            echo "Base de données mise à jour";
        } else {
            echo "Erreur d'insertion: " . $mysqli->error;
        }
    }
    $mysqli->close();
}
?>