package Project.BootImitation;

import static Project.Models.ConsoleColors.*;

public class BootImitaion {
    public static void systemStartup() throws InterruptedException{
        String[] logs = {
                "[    0.000000] Initializing DigiUni Kernel ver 2.0.26-release",
                "[    0.000024] CPU: GenuineIntel family 6 model 158 stepping 13",
                "[    0.102341] Memory: 32GB (LPDDR4 4266MHz)",
                "[    0.156002] ACPI: Core System Control Enabled",
                "[    0.289110] PCI: Enumerating devices...",
                "[    0.450219] USB: Root Hub created (USB 3.1)",
                "[    0.512000] Storage: NVMe SSD 1TB /dev/nvme0n1 detected",
                "[    0.680012] File System: Mounting /root... OK",
                "[    0.801233] Network: Establishing connection eth0... done",
                "[    1.023441] Security: SELinux initialized (enforcing)",
                " ",
                "[ MODULE ] Starting DigiUni Repository Framework...",
                "[  OK  ] FacultyRepository initialized at 0x7ffd51",
                "[  OK  ] DepartmentRepository mapping students and teachers",
                "[  OK  ] StudentRepository checking data integrity",
                "[  OK  ] TeacherRepository loading faculty links",
                "[  OK  ] UniversityService: singleton instance created",
                " ",
                "[ CONFIG ] Scanning for persistent data files...",
                "[ INFO ] Found faculties.json (48KB)",
                "[ INFO ] Found departments.json (124KB)",
                "[ INFO ] Found students.txt (2.4MB)",
                "[ INFO ] Found teachers.xml (156KB)",
                "[  OK  ] Synchronizing database state... Done",
                " ",
                "[ SYSTEM ] Starting CLI Environment...",
                "[ SYSTEM ] Setting colors: CYAN, YELLOW, GREEN, RED",
                "[ SYSTEM ] Checking user permissions: ADMIN_LEVEL",
                "[ SYSTEM ] Loading Terminal UI Kit...",
                " ",
                "DIGIUNI(TM) BIOS RELEASE 2026",
                "COPYRIGHT (C) 2026 NAUKMA SOFTWARE ENGINEERING"
        };

        System.out.println( "SYSTEM BOOT SEQUENCE" );
        System.out.println(CYAN + "========================================" + RESET);

        for (int i = 0; i < logs.length; i++) {
            String log = logs[i];

            if (log.contains("[  OK  ]")) {
                log = log.replace("[  OK  ]", "[" + GREEN + "  OK  " + RESET + "]");
            }
            else if (log.contains("[ INFO ]")) {
                log = log.replace("[ INFO ]", "[" + CYAN + " INFO " + RESET + "]");
            }
            else if (log.contains("[ MODULE ]") || log.contains("[ CONFIG ]")) {
                log = log.replace("[", "[" + YELLOW).replace("]", RESET + "]");
            }

            System.out.println(log);

            if (i % 5 == 0) {
                Thread.sleep((long) (Math.random() * 200 + 400));
            } else {
                Thread.sleep((long) (Math.random() * 100 + 50));
            }
        }

        System.out.print("\n"  + "PREPARING INTERFACE " + RESET);
        for (int i = 0; i < 30; i++) {
            System.out.print(CYAN + "█" + RESET);
            Thread.sleep(60);
        }
        System.out.println(GREEN + " 100%" + RESET);

        Thread.sleep(800);

        System.out.println("\n".repeat(100));
    }
    }

