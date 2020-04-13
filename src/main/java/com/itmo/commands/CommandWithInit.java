package com.itmo.commands;

import java.util.Scanner;

public interface CommandWithInit {
    void init(String argument, Scanner scanner);
}
