package hw4cs3310_Khan;

import java.io.*;
import java.util.*;

/**
 * This program has the ability to store integers, get their median and delete their median/upper median (depending on
 * count of the stored numbers). It is also able to sort stored numbers using their medians as well as merge sort.
 * User can choose to interactively store numbers and get and delete medians, see results for a hardcoded sequence of
 * instructions or store instructions in a file named 'hw4input.txt' in the source folder, get time taken for
 * operations at cardinalities 5, 50, 100, 150 and 200 along with total average time for add, get and delete and, in
 * the end, sorted versions of the stored numbers.
 *
 * This program was developed using IntelliJ IDE and an extracted (folder) version of it (from .zip file) can be
 * opened from within IntelliJ which can be run thereafter.
 * Output for the input sequence 'a 10, a 12, a 5, a 7, g, d, g, a 2, a 20, d, g, d, d, d, g, g, d, d, d' is given
 * (by choosing 2) at runtime.
 *
 * @author M. Salman Khan
 */
class Main {

    /**
     * Main method takes inputs, calls other methods to accomplish certain tasks and outputs
     * @param args not used
     * @throws Exception can be thrown if an error occurs
     */
    public static void main(String[] args) throws Exception {

        Heaps s = new Heaps();
        LinkedListMinHeap qPlus = s.createQPlus();
        ArrayMaxHeap qMinus = s.createQMinus();
        StepDArray singleArray = new StepDArray();
        // DESIGN REQUIREMENTS
        String reqSeq = "a 10, a 12, a 5, a 7, g, d, g, a 2, a 20, d, g, d, d, d, g, g, d, d, d";
        String[] hardCodeIns = reqSeq.split(", ");
        Scanner kbdTxt = new Scanner(System.in);
        Scanner kbdNum = new Scanner(System.in);
        // STEP h
        Scanner input = new Scanner(new File("hw4input.txt"));
        String[] instructions = input.nextLine().split(",");
        int size = 0;
        int[] medSortRes;
        int[] mergeSortRes;
        long stTime;
        long enTime;
        long sumAddTime = 0;
        long sumGetTime = 0;
        long sumDelTime = 0;
        long medSortTime;
        long mergeSortTime;
        int addCount = 0;
        int getCount = 0;
        int delCount = 0;
        String addedIn;
        HashSet<Integer> printedAdd = new HashSet<>();
        HashSet<Integer> printedGet = new HashSet<>();
        HashSet<Integer> printedDel = new HashSet<>();
        HashSet<Integer> printedSort = new HashSet<>();

        for (int i = 0; i < instructions.length; i++) {
            if (instructions[i].charAt(0) == 'a')
                size++;
        }

        qMinus.assignSize(size);
        singleArray.assignSize(size);

        int choice;
        System.out.print(">1 - interactive prompts?\n" +
                ">2 - execute instructions contained in the sequence specified in design requirements section?\n" +
                ">3 - execute instructions contained in hw4input.txt file?\n" +
                ">");
        choice = kbdNum.nextInt();

        System.out.print("\n");

        switch (choice) {
            // STEP g
            case 1:
            while (true) {
                System.out.print(">A – add?\n" +
                        ">C – check median?\n" +
                        ">D – delete median?\n" +
                        ">E – exit?\n" +
                        ">");
                char resp = kbdTxt.next().toUpperCase().charAt(0);
                switch (resp) {
                    case 'A':
                        System.out.print("> ok, specify the value v to be inserted into S\n" +
                                ">");
                        int toAdd = kbdNum.nextInt();
                        System.out.printf("> done, value %d is inserted in %s,\n%s\n\n", toAdd, s.add(toAdd),
                                s.getInOrder());
                        break;
                    case 'C':
                        if (s.getTotCount() != 0) {
                            System.out.printf(">the median is %d\n\n", s.getMedian());
                        } else {
                            System.out.println(">heap is empty\n");
                        }
                        break;
                    case 'D':
                        if (s.getTotCount() != 0) {
                            int toDel = qPlus.getRootVal();
                            s.deleteMedian();
                            if (s.getTotCount() % 2 == 0) {
                                System.out.printf(">median %d is deleted from Q+.\n%s\n\n", toDel, s.getInOrder());
                            } else {
                                System.out.printf(">upper median %d is deleted from Q+\n%s\n\n", toDel,
                                        s.getInOrder());
                            }
                        } else {
                            System.out.println(">heap is empty\n");
                        }
                        break;
                    case 'E':
                        kbdNum.close();
                        kbdTxt.close();
                        System.exit(0);
                    default:
                        System.out.println("> invalid entry\n");
                }
            }
            // DESIGN REQUIREMENTS
            case 2:
                for (int i = 0; i < hardCodeIns.length; i++) {
                    System.out.println(">" + hardCodeIns[i]);
                    switch (hardCodeIns[i].toUpperCase().charAt(0)) {
                        case 'A':
                            int toAdd = Integer.parseInt(hardCodeIns[i].substring(2));
                            stTime = System.nanoTime();
                            addedIn = s.add(toAdd);
                            enTime = System.nanoTime();
                            sumAddTime += (enTime - stTime);
                            addCount++;
                            System.out.printf("> value %d is inserted in %s,\n%s\n\n", toAdd, addedIn,
                                    s.getInOrder());
                            break;
                        case 'G':
                            if (s.getTotCount() != 0) {
                                stTime = System.nanoTime();
                                System.out.printf(">the median is %d\n\n", s.getMedian());
                                enTime = System.nanoTime();
                                sumGetTime += (enTime - stTime);
                                getCount++;
                            } else {
                                System.out.println(">heap is empty\n");
                            }
                            break;
                        case 'D':
                            if (s.getTotCount() != 0) {
                                int toDel = qPlus.getRootVal();
                                stTime = System.nanoTime();
                                s.deleteMedian();
                                enTime = System.nanoTime();
                                sumDelTime += (enTime - stTime);
                                delCount++;
                                if (s.getTotCount() % 2 == 0) {
                                    System.out.printf(">median %d is deleted from Q+.\n%s\n\n", toDel, s.getInOrder());
                                } else {
                                    System.out.printf(">upper median %d is deleted from Q+\n%s\n\n", toDel,
                                            s.getInOrder());
                                }
                            } else {
                                System.out.println(">heap is empty\n");
                            }
                            break;
                        default:
                            System.out.println("> invalid entry\n");
                    }

                    switch (hardCodeIns[i].toUpperCase().charAt(0)) {
                        case 'A':
                            int toAdd = Integer.parseInt(hardCodeIns[i].substring(2));
                            singleArray.add(toAdd);
                            break;
                        case 'D':
                            singleArray.deleteMedian();
                            break;
                    }
                }
                System.out.printf(">average time for add: %d nanoseconds\n" +
                                ">average time for getMedian: %d nanoseconds\n" +
                                ">average time for deleteMedian: %d nanoseconds\n", (sumAddTime/addCount),
                        (sumGetTime/getCount), (sumDelTime/delCount));
                System.out.println("\n>contents of sorted array created using sortUsingMedians method:");
                stTime = System.nanoTime();
                medSortRes = Sort.sortUsingMedians(s);
                enTime = System.nanoTime();
                medSortTime = (enTime - stTime);
                for (int j = 0; j < medSortRes.length; j++) {
                    if (j != 0)
                        System.out.print(", ");
                    System.out.print(medSortRes[j]);
                }
                System.out.printf("\n>time for sortUsingMedians: %d nanoseconds\n", medSortTime);
                System.out.println("\n>contents of sorted array created using merge sort:");
                stTime = System.nanoTime();
                mergeSortRes = Sort.mergeSort(Sort.getShuffled());
                enTime = System.nanoTime();
                mergeSortTime = (enTime - stTime);
                for (int j = 0; j < mergeSortRes.length; j++) {
                        if (j != 0)
                            System.out.print(", ");
                        System.out.print(mergeSortRes[j]);
                }
                System.out.printf("\n>time for merge sort: %d nanoseconds\n\n", mergeSortTime);
                System.out.printf(">%s took %d nanoseconds more\n",
                        ((medSortTime < mergeSortTime) ? "merge sort" : "sortUsingMedians"),
                        ((medSortTime < mergeSortTime) ? (mergeSortTime - medSortTime) :
                                (medSortTime - mergeSortTime)));
                break;
            // STEP h
            case 3:
                for (int i = 0; i < instructions.length; i++) {
                    switch (instructions[i].toUpperCase().charAt(0)) {
                        case 'A':
                            int toAdd = Integer.parseInt(instructions[i].substring(2));
                            stTime = System.nanoTime();
                            s.add(toAdd);
                            enTime = System.nanoTime();
                            if (!printedAdd.contains(s.getTotCount()) && (s.getTotCount() == 6 || s.getTotCount() == 51
                                    || s.getTotCount() == 101 || s.getTotCount() == 151 || s.getTotCount() == 201)) {
                                System.out.printf(">time taken to add: %d nanoseconds - cardinality of set: %d\n",
                                        (enTime - stTime), (s.getTotCount() - 1));
                                printedAdd.add(s.getTotCount());
                            }
                            sumAddTime += (enTime - stTime);
                            addCount++;
                            break;
                        case 'G':
                            if (s.getTotCount() != 0) {
                                stTime = System.nanoTime();
                                s.getMedian();
                                enTime = System.nanoTime();
                                if (!printedGet.contains(s.getTotCount()) && (s.getTotCount() == 5 ||
                                        s.getTotCount() == 50 || s.getTotCount() == 100 || s.getTotCount() == 150 ||
                                        s.getTotCount() == 200)) {
                                    System.out.printf(">time taken to get median : %d nanoseconds - cardinality of " +
                                            "set: %d\n", (enTime - stTime), s.getTotCount());
                                    printedGet.add(s.getTotCount());
                                }
                                sumGetTime += (enTime - stTime);
                                getCount++;
                            }
                            break;
                        case 'D':
                            if (s.getTotCount() != 0) {
                                stTime = System.nanoTime();
                                s.deleteMedian();
                                enTime = System.nanoTime();
                                if (!printedDel.contains(s.getTotCount()) && (s.getTotCount() == 4 ||
                                        s.getTotCount() == 49 || s.getTotCount() == 99 || s.getTotCount() == 149 ||
                                        s.getTotCount() == 199)) {
                                    System.out.printf(">time taken to delete median: %d nanoseconds - cardinality " +
                                            "of set: %d\n", (enTime - stTime), (s.getTotCount() + 1));
                                    printedDel.add(s.getTotCount());
                                }
                                sumDelTime += (enTime - stTime);
                                delCount++;
                            }
                            break;
                    }

                    switch (instructions[i].toUpperCase().charAt(0)) {
                        case 'A':
                            int toAdd = Integer.parseInt(instructions[i].substring(2));
                            singleArray.add(toAdd);
                            break;
                        case 'D':
                            singleArray.deleteMedian();
                            break;
                    }

                    if (!printedSort.contains(s.getTotCount()) && (s.getTotCount() == 5 ||
                            s.getTotCount() == 50 || s.getTotCount() == 100 || s.getTotCount() == 150 ||
                            s.getTotCount() == 200)) {
                        Heaps toSortNow = (Heaps) makeDeepCopy(s);
                        stTime = System.nanoTime();
                        medSortRes = Sort.sortUsingMedians(toSortNow);
                        enTime = System.nanoTime();
                        System.out.printf(">time taken to sort using medians: %d nanoseconds - cardinality " +
                                "of set: %d\n", (enTime - stTime), (s.getTotCount()));
                        stTime = System.nanoTime();
                        mergeSortRes = Sort.mergeSort(Sort.getShuffled());
                        enTime = System.nanoTime();
                        System.out.printf(">time taken to merge sort: %d nanoseconds - cardinality " +
                                "of set: %d\n", (enTime - stTime), (s.getTotCount()));
                        printedSort.add(s.getTotCount());
                    }
                }
                System.out.printf("\n>average time for add: %d nanoseconds\n" +
                        ">average time for getMedian: %d nanoseconds\n" +
                        ">average time for deleteMedian: %d nanoseconds\n", (sumAddTime/addCount),
                        (sumGetTime/getCount), (sumDelTime/delCount));
                System.out.println("\n>contents of sorted array created using sortUsingMedians method:");
                stTime = System.nanoTime();
                medSortRes = Sort.sortUsingMedians(s);
                enTime = System.nanoTime();
                medSortTime = (enTime - stTime);
                for (int j = 0; j < medSortRes.length; j++) {
                    if (j != 0)
                        System.out.print(", ");
                    System.out.print(medSortRes[j]);
                }
                System.out.printf("\n>time for sortUsingMedians: %d nanoseconds\n", medSortTime);
                System.out.println("\n>contents of sorted array created using merge sort:");
                stTime = System.nanoTime();
                mergeSortRes = Sort.mergeSort(Sort.getShuffled());
                enTime = System.nanoTime();
                mergeSortTime = (enTime - stTime);
                for (int j = 0; j < mergeSortRes.length; j++) {
                    if (j != 0)
                        System.out.print(", ");
                    System.out.print(mergeSortRes[j]);
                }
                System.out.printf("\n>time for merge sort: %d nanoseconds\n\n", mergeSortTime);
                System.out.printf(">%s took %d nanoseconds more\n",
                        ((medSortTime < mergeSortTime) ? "merge sort" : "sortUsingMedians"),
                        ((medSortTime < mergeSortTime) ? (mergeSortTime - medSortTime) :
                                (medSortTime - mergeSortTime)));
                break;
            default:
                System.out.println("> invalid entry");
        }

        kbdNum.close();
        kbdTxt.close();

    }

    /**
     * Makes deep copies of objects
     * @param object receives address of the object that is to be deep copied
     * @return address of a deep copy of the received object
     * @throws Exception if an error occurs
     */
    private static Object makeDeepCopy (Object object) throws Exception {
        // following code was copied from https://www.journaldev.com/17129/java-deep-copy-object
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
        outputStrm.writeObject(object);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
        return objInputStream.readObject();
    }

}
