package com.flooringorder.dao;

import com.flooringorder.model.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class OrderDaoFileImpl implements OrderDao {

    private final String ORDER_DIRECTORY_PATH;
    private final String EXPORT_DIRECTORY_PATH;
    private static final String ORDER_FILE_NAME = "Orders_";
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
    private static final DateTimeFormatter formatterISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private HashMap<LocalDate, HashMap<Integer,Order>> ordersMap = new HashMap<>();

    public OrderDaoFileImpl() {
        ORDER_DIRECTORY_PATH = "src/main/java/com/flooringorder/SampleFileData/Orders/";
        EXPORT_DIRECTORY_PATH = "src/main/java/com/flooringorder/SampleFileData/Export/";
    }

    public OrderDaoFileImpl(String ORDER_FILE_PATH, String EXPORT_DIRECTORY_PATH) {
        this.ORDER_DIRECTORY_PATH = ORDER_FILE_PATH;
        this.EXPORT_DIRECTORY_PATH = EXPORT_DIRECTORY_PATH;
    }

    @Override
    public Order addOrder(Order newOrder, LocalDate date) {
        return null;
    }

    /*
     *  Retrieve a list of Order object based on a date
     *  Return null if nothing found
     * */
    @Override
    public List<Order> getAllOrderByDate(LocalDate date) throws DataPersistanceException {
        loadAllOrders();
        HashMap<Integer, Order> ordersFound = ordersMap.get(date);
        return (ordersFound == null) ? null : new ArrayList<>(ordersFound.values());
    }

    @Override
    public Order removeOrder(int orderId, LocalDate date) {
        return null;
    }

    @Override
    public Order updateOrder(int orderId, LocalDate date) {
        return null;
    }

    /*
    *  Retrieve an Order based on OrderId and Date
    *  Return null if nothing found
    * */
    @Override
    public Order getOrderByIdAndDate(int orderId, LocalDate date) throws DataPersistanceException {
        loadAllOrders();
        HashMap<Integer, Order> ordersFound = ordersMap.get(date);
        return (ordersFound == null) ? null : ordersFound.get(orderId);
    }

    @Override
    public void exportAll() throws DataPersistanceException {
        this.loadAllOrders();
    }

    private List<String> getAllOrdersFileName() {
        File orderDirectory = new File(ORDER_DIRECTORY_PATH);
        File[] ordersFiles = orderDirectory.listFiles();
        List<String> listOrderFilesName = new ArrayList<>();
        if(ordersFiles != null) {
            for(File currentFile: ordersFiles) {
                listOrderFilesName.add(currentFile.getName());
            }
        }

        return listOrderFilesName;
    }

    private void loadAllOrders() throws DataPersistanceException {
        List<String> ordersFileName = this.getAllOrdersFileName();
        for(String filename: ordersFileName) {
            // retrieve date from the file name in format MMddyyyy
            LocalDate currentDate = LocalDate.parse(filename.substring(7, 15), formatter);
            // load order data into memory with the date in ISO format
            loadOrder(filename, LocalDate.parse(currentDate.format(formatterISO)) );
        }
    }

    /*
    * Loads all orders from a file that match specified date into memory : Orders_<date>.txt
    * */
    private void loadOrder(String fileName, LocalDate date) throws DataPersistanceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(ORDER_DIRECTORY_PATH + fileName)));
        } catch (FileNotFoundException e) {
            throw new DataPersistanceException("Could Not load order data into memory", e);
        }

        String currentLine;
        Order currentOrder;
        List<Order> orders = new ArrayList<>();

        // skip header line to avoid conflict in unmarshallOrder()
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine, date);
            orders.add(currentOrder);

            // if we have no data on this date, create a placeholder HashMap
            ordersMap.putIfAbsent(date, new HashMap<>());
            ordersMap.get(date).put(currentOrder.getOrderId(), currentOrder);
        }

    }

    /*
    * Generate a file name based on the given date : Order_MMDDYYYY.txt
    * */
    private String generateOrderFileName(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        StringBuilder filename = new StringBuilder();
        filename.append(ORDER_FILE_NAME);
        filename.append(date.format(formatter));
        filename.append(".txt");
        return filename.toString();
    }

    private void writeOrder(LocalDate date) throws DataPersistanceException, UnsupportedEncodingException {
        throw new UnsupportedEncodingException();
    }

    /*
     * Convert an Order object into a String
     * */
    private String marshallOrder(Order order){
        String orderAsText;

        orderAsText = order.getOrderId() + DELIMITER;
        orderAsText += order.getCustomerName() + DELIMITER;
        orderAsText += order.getState() + DELIMITER;
        orderAsText += order.getTaxRate().toString() + DELIMITER;
        orderAsText += order.getProductType() + DELIMITER;
        orderAsText += order.getArea().toString() + DELIMITER;
        orderAsText += order.getCostPerSquareFoot().toString() + DELIMITER;
        orderAsText += order.getLaborCostPerSquareFoot().toString() + DELIMITER;
        orderAsText += order.getMaterialCost().toString() + DELIMITER;
        orderAsText += order.getLaborCost().toString() + DELIMITER;
        orderAsText += order.getTax().toString() + DELIMITER;
        orderAsText += order.getTotal().toString();

        return orderAsText;
    }

    /*
     * Convert a String of Order into an Order Object
     * */
    private Order unmarshallOrder(String orderAsText, LocalDate date) {
        String[] orderTokens = orderAsText.split(DELIMITER);
        /*
        0: OrderNumber – Integer
        1: CustomerName – String
        2: State – String
        3: TaxRate – BigDecimal
        4: ProductType – String
        5: Area – BigDecimal
        6: CostPerSquareFoot – BigDecimal
        7: LaborCostPerSquareFoot – BigDecimal
        8: MaterialCost – BigDecimal
        9: LaborCost – BigDecimal
        10: Tax – BigDecimal
        11: Total – BigDecimal
        */
        int orderId = Integer.parseInt(orderTokens[0]);
        String customerName = orderTokens[1];
        String state = orderTokens[2];
        BigDecimal taxRate = new BigDecimal(orderTokens[3]).setScale(2, RoundingMode.HALF_UP);
        String productType = orderTokens[4];
        BigDecimal area = new BigDecimal(orderTokens[5]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal costPerSquareFoot = new BigDecimal(orderTokens[6]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(orderTokens[7]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal materialCost = new BigDecimal(orderTokens[8]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = new BigDecimal(orderTokens[9]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = new BigDecimal(orderTokens[10]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = new BigDecimal(orderTokens[11]).setScale(2, RoundingMode.HALF_UP);


        Order orderFromFile = new Order(date, orderId);
        orderFromFile.setCustomerName(customerName);
        orderFromFile.setState(state);
        orderFromFile.setTaxRate(taxRate);
        orderFromFile.setProductType(productType);
        orderFromFile.setArea(area);
        orderFromFile.setCostPerSquareFoot(costPerSquareFoot);
        orderFromFile.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        orderFromFile.setMaterialCost(materialCost);
        orderFromFile.setLaborCost(laborCost);
        orderFromFile.setTax(tax);
        orderFromFile.setTotal(total);
        return orderFromFile;
    }


}
