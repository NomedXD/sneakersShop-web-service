package by.teachmeskills.sneakersshopwebserviceexam.enums;

public interface EshopConstants {
    Integer PAGE_SIZE = 5;
    Integer TOTAL_PAGINATED_VISIBLE_PAGES = 5;
    String successfulExportMessage = "Successful exported. Check your desktop";
    String errorOrdersExportMessage = "Cannot export orders to file";
    String errorOrdersImportMessage = "Cannot import orders from file";
    String errorFileNullMessage = "Cannot import orders because file is empty";
    String errorProductsExportMessage = "Cannot export products to file";
    String errorProductsImportMessage = "Cannot import products from file";
    String errorCategoriesExportMessage = "Cannot export categories to file";
    String errorCategoriesImportMessage = "Cannot import categories from file";
    String categoriesFilePath = "src/main/resources/categories.csv";
}
