/*
 * This Class will read through Persons.dat, Assets.dat and Portfolios.dat then
 * convert them into an Array list through scanner then through tokenization it
 * assigns the values into array list respectively
 * 
 * package com.tbf;
 * 
 * import java.io.File; import java.io.FileNotFoundException; import
 * java.util.ArrayList; import java.util.Arrays; import java.util.Collections;
 * import java.util.Comparator; import java.util.Scanner;
 * 
 * public class FileReader { public ArrayList<Asset> Assets() { ArrayList<Asset>
 * a = new ArrayList<Asset>(); int count = 0; Scanner scan = null; String
 * line[];
 * 
 * try { scan = new Scanner(new File("data/Assets.dat"));
 * 
 * } catch (FileNotFoundException e) { e.printStackTrace(); }
 * 
 * count = scan.nextInt();
 * 
 * while (scan.hasNextLine()) { line = scan.nextLine().split(";");
 * 
 * // Deposit Account if (line.length == 4) { Deposit d = new Deposit(line[0],
 * line[1], (line[2]), Double.parseDouble(line[3]) / 100.0); a.add(d); }
 * 
 * // Stocks Account else if (line.length == 8) { Stock s = new Stock(line[0],
 * line[1], line[2], Double.parseDouble(line[3]), Double.parseDouble(line[4]) /
 * 100.0, Double.parseDouble(line[5]), (line[6]), Double.parseDouble(line[7]));
 * a.add(s); }
 * 
 * // Private Account else if (line.length == 7) { PrivateInvestment p = new
 * PrivateInvestment(line[0], line[1], line[2], Double.parseDouble(line[3]),
 * Double.parseDouble(line[4]) / 100.0, Double.parseDouble(line[5]),
 * Double.parseDouble(line[6])); a.add(p); } } scan.close(); return a;
 * 
 * }
 * 
 * public ArrayList<Person> Person() { ArrayList<Person> p = new
 * ArrayList<Person>(); int count = 0; Scanner scan2 = null; String[] line;
 * 
 * try { scan2 = new Scanner(new File("data/Persons.dat")); } catch
 * (FileNotFoundException e) { e.printStackTrace(); }
 * 
 * count = scan2.nextInt(); scan2.nextLine();
 * 
 * while (scan2.hasNextLine()) { line = scan2.nextLine().split(";"); String code
 * = line[0]; String brokerdata; String[] line2; String broker = ""; String
 * secCode = ""; if (line[1].compareTo("") == 0) { brokerdata = ""; broker = "";
 * secCode = ""; } else { brokerdata = line[1]; line2 = line[1].split(",");
 * broker = line2[0]; secCode = line[1]; }
 * 
 * String lastName = line[2].split(",")[0]; String firstName =
 * line[2].split(",")[1]; String[] address = line[3].split(","); String street =
 * address[0]; String city = address[1]; String state = address[2]; String zip =
 * address[3]; String country = address[4]; Address add = new Address(street,
 * city, state, zip, country); ArrayList<String> emailarr = null; // for loop
 * for expectional bound if (line.length >= 5) { String[] email =
 * line[4].split(",");
 * 
 * emailarr = new ArrayList<String>();
 * 
 * if (line[4].compareTo("") != 0) { int num = email.length; for (int i = 0; i <
 * num; i++) { emailarr.add(email[i]); } } }
 * 
 * // Setting People into different subclasses to type cast if
 * (broker.equals("E")) { Expert exp = new Expert(code, broker, secCode,
 * lastName, firstName, add, emailarr); p.add(exp); }
 * 
 * else if (broker.equals("J")) { Junior jun = new Junior(code, broker, secCode,
 * lastName, firstName, add, emailarr); p.add(jun); }
 * 
 * else { Person pep = new Person(code, lastName, firstName, add, emailarr);
 * p.add(pep); } } scan2.close(); return p;
 * 
 * }
 * 
 * public ArrayList<Portfolio> readingPortfolios(ArrayList<Asset> masterAssets,
 * ArrayList<Person> masterPerson) { ArrayList<Portfolio> po = new
 * ArrayList<>(); int count = 0; Scanner scan3 = null; String[] line;
 * 
 * try { scan3 = new Scanner(new File("data/Portfolios.dat")); } catch
 * (FileNotFoundException e) { e.printStackTrace(); }
 * 
 * count = scan3.nextInt(); scan3.nextLine();
 * 
 * while (scan3.hasNextLine()) { line = scan3.nextLine().split(";"); if
 * (line.length == 1) { break;// check for out of bound exceptions } String
 * portCode = line[0]; String ownerCode = line[1]; Person owner = null; for (int
 * i = 0; i < masterPerson.size(); i++) { if
 * (masterPerson.get(i).getCode().equals(ownerCode)) { owner =
 * masterPerson.get(i); } } String managerCode = line[2]; Person manager = null;
 * for (int i = 0; i < masterPerson.size(); i++) { if
 * (masterPerson.get(i).getCode().equals(managerCode)) { manager =
 * masterPerson.get(i); } } ArrayList<Asset> portfolioAssets = new
 * ArrayList<Asset>();
 * 
 * String beneficialCode = null; Person benefical = null;
 * 
 * if (line.length > 3)// check for out of bound exceptions { if
 * (line[3].compareTo("") == 0) { beneficialCode = ""; } else { beneficialCode =
 * line[3]; for (int i = 0; i < masterPerson.size(); i++) { if
 * (masterPerson.get(i).getCode().equals(beneficialCode)) { benefical =
 * masterPerson.get(i); } } }
 * 
 * if (line.length > 4)// check for out of bound exceptions {
 * 
 * String assetsToken[] = line[4].split(","); for (int i = 0; i <
 * assetsToken.length; i++) { String valueToken[] = assetsToken[i].split(":");
 * String code = valueToken[0]; double numValue =
 * Double.parseDouble(valueToken[1]);
 * 
 * // finding the asset associated with the code we got for (Asset x :
 * masterAssets) { if (x.getCode().equals(code)) { Asset temp = x;
 * 
 * if (temp.getType().equals("S")) { // copy constructor set up Stock stock =
 * new Stock((Stock) temp, numValue); portfolioAssets.add(stock);
 * 
 * }
 * 
 * else if (temp.getType().equals("D")) { // copy constructor set up Deposit
 * deposit = new Deposit((Deposit) temp, numValue);
 * portfolioAssets.add(deposit); }
 * 
 * else if (temp.getType().equals("P")) { // copy constructor set up
 * PrivateInvestment pvtinv = new PrivateInvestment((PrivateInvestment) temp,
 * numValue); portfolioAssets.add(pvtinv);
 * 
 * } }
 * 
 * }
 * 
 * }
 * 
 * } }
 * 
 * Portfolio port = new Portfolio(portCode, owner, manager, benefical,
 * portfolioAssets); po.add(port); } scan3.close(); Collections.sort(po , new
 * Comparator<Portfolio>() {
 * 
 * @Override public int compare(Portfolio a, Portfolio b) { return
 * a.getOwner().getlastName().compareTo(b.getOwner().getlastName()); } });
 * 
 * return po; }
 * 
 * }
 */