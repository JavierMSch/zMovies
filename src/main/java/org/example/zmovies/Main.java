package org.example.zmovies;

import java.util.Scanner;
import org.example.zmovies.Modelos.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VideoClub videoClub = new VideoClub();
        InterfazUsuario ui = new InterfazUsuario(scanner, videoClub);

        ui.start();

        scanner.close();
    }
}