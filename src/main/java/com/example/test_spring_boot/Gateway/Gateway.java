package com.example.test_spring_boot.Gateway;


import com.example.test_spring_boot.Dto.NganLuongDto.RequestCheckOrder;
import com.example.test_spring_boot.Dto.NganLuongDto.RequestInfo;
import com.example.test_spring_boot.Dto.NganLuongDto.ResponseCheckOrder;
import com.example.test_spring_boot.Dto.NganLuongDto.ResponseInfo;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

/**
 *
 * @author TaiND
 */
public class Gateway {

    // link api live
//    private final String query = "https://www.nganluong.vn/checkout.api.nganluong.post.php";
    // link api test
    private final String query = "https://sandbox.nganluong.vn:8088/nl30/checkout.api.nganluong.post.php";

    public String getUrlToken(RequestInfo info) throws Exception {
        return query + "?" + GetParamPost(info);
    }

    public ResponseInfo chage(RequestInfo info) throws Exception {
        String result = callUrl(query, GetParamPost(info), "");
        result = result.replace("&", "&amp;");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(result));

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("result");

        ResponseInfo response = new ResponseInfo();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            NodeList name = element.getElementsByTagName("error_code");
            Element line = (Element) name.item(0);
            response.setError_code(getCharacterDataFromElement(line));

            NodeList title = element.getElementsByTagName("token");
            line = (Element) title.item(0);
            response.setToken(getCharacterDataFromElement(line));

            NodeList description = element.getElementsByTagName("description");
            line = (Element) description.item(0);
            response.setDescription(getCharacterDataFromElement(line));

            NodeList checkout_url = element.getElementsByTagName("checkout_url");
            line = (Element) checkout_url.item(0);
            response.setCheckout_url(getCharacterDataFromElement(line));
        }
        return response;
    }

    public ResponseCheckOrder checkOrder(RequestCheckOrder info) throws Exception {
        String request = "";
        request += "function=" + info.getFuntion();
        request += "&version=" + info.getVersion();
        request += "&merchant_id=" + info.getMerchant_id();
        request += "&merchant_password=" + CreateMD5Hash(info.getMerchant_password());
        request += "&token=" + info.getToken();
        String result = callUrl(query, request, "");
        result = result.replace("&", "&amp;");
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(result));

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("result");

        ResponseCheckOrder response = new ResponseCheckOrder();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            NodeList name = element.getElementsByTagName("error_code");
            Element line = (Element) name.item(0);
            response.setError_code(getCharacterDataFromElement(line));

            NodeList title = element.getElementsByTagName("token");
            line = (Element) title.item(0);
            response.setToken(getCharacterDataFromElement(line));
            
            NodeList description = element.getElementsByTagName("description");
            line = (Element) description.item(0);
            response.setDescription(getCharacterDataFromElement(line));
            
            NodeList transaction_status = element.getElementsByTagName("transaction_status");
            line = (Element) transaction_status.item(0);
            response.setTransaction_status(getCharacterDataFromElement(line));
            
            NodeList receiver_email = element.getElementsByTagName("receiver_email");
            line = (Element) receiver_email.item(0);
            response.setReceiver_email(getCharacterDataFromElement(line));
            
            NodeList order_code = element.getElementsByTagName("order_code");
            line = (Element) order_code.item(0);
            response.setOrder_code(getCharacterDataFromElement(line));
            
            NodeList total_amount = element.getElementsByTagName("total_amount");
            line = (Element) total_amount.item(0);
            response.setTotal_amount(getCharacterDataFromElement(line));
            
            NodeList payment_method = element.getElementsByTagName("payment_method");
            line = (Element) payment_method.item(0);
            response.setPayment_method(getCharacterDataFromElement(line));
            
            NodeList bank_code = element.getElementsByTagName("bank_code");
            line = (Element) bank_code.item(0);
            response.setBank_code(getCharacterDataFromElement(line));
            
            NodeList payment_type = element.getElementsByTagName("payment_type");
            line = (Element) payment_type.item(0);
            response.setPayment_type(getCharacterDataFromElement(line));
            
            NodeList order_description = element.getElementsByTagName("order_description");
            line = (Element) order_description.item(0);
            response.setOrder_description(getCharacterDataFromElement(line));
            
            NodeList tax_amount = element.getElementsByTagName("tax_amount");
            line = (Element) tax_amount.item(0);
            response.setTax_amount(getCharacterDataFromElement(line));
            
            NodeList discount_amount = element.getElementsByTagName("discount_amount");
            line = (Element) discount_amount.item(0);
            response.setDiscount_amount(getCharacterDataFromElement(line));
            
            NodeList fee_shipping = element.getElementsByTagName("fee_shipping");
            line = (Element) fee_shipping.item(0);
            response.setFee_shipping(getCharacterDataFromElement(line));
            
            NodeList return_url = element.getElementsByTagName("return_url");
            line = (Element) return_url.item(0);
            response.setReturn_url(getCharacterDataFromElement(line));
            
            NodeList cancel_url = element.getElementsByTagName("cancel_url");
            line = (Element) cancel_url.item(0);
            response.setCancel_url(getCharacterDataFromElement(line));
            
            NodeList buyer_fullname = element.getElementsByTagName("buyer_fullname");
            line = (Element) buyer_fullname.item(0);
            response.setBuyer_fullname(getCharacterDataFromElement(line));
            
            NodeList buyer_email = element.getElementsByTagName("buyer_email");
            line = (Element) buyer_email.item(0);
            response.setBuyer_email(getCharacterDataFromElement(line));
            
            NodeList buyer_mobile = element.getElementsByTagName("buyer_mobile");
            line = (Element) buyer_mobile.item(0);
            response.setBuyer_mobile(getCharacterDataFromElement(line));
            
            NodeList buyer_address = element.getElementsByTagName("buyer_address");
            line = (Element) buyer_address.item(0);
            response.setBuyer_address(getCharacterDataFromElement(line));
            
            NodeList affiliate_code = element.getElementsByTagName("affiliate_code");
            line = (Element) affiliate_code.item(0);
            response.setAffiliate_code(getCharacterDataFromElement(line));
            
            NodeList transaction_id = element.getElementsByTagName("transaction_id");
            line = (Element) transaction_id.item(0);
            response.setTransaction_id(getCharacterDataFromElement(line));
        }
        return response;
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public static String callUrl(String strUrl, String query, String type) throws Exception {
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setReadTimeout(180000);
        con.setDoOutput(true);
        OutputStream outputStream = con.getOutputStream();
        try (DataOutputStream wr = new DataOutputStream((outputStream))) {
            wr.writeBytes(query);
            wr.flush();
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
        }
        String rsp = response.toString();
        rsp = rsp.substring(0, rsp.length() - 1);
        return rsp;
    }

    private static String GetParamPost(RequestInfo info) throws Exception {
        String request = "";
        request += "function=" + info.getFuntion();
        request += "&cur_code=" + info.getCur_code();
        request += "&version=" + info.getVersion();
        request += "&merchant_id=" + info.getMerchant_id();
        request += "&receiver_email=" + info.getReceiver_email();
        request += "&merchant_password=" + CreateMD5Hash(info.getMerchant_password());
        request += "&order_code=" + info.getOrder_code();
        request += "&total_amount=" + info.getTotal_amount();
        request += "&payment_method=" + info.getPayment_method();
        request += "&bank_code=" + info.getBank_code();
        request += "&payment_type=" + info.getPayment_type();
        request += "&order_description=" + info.getOrder_description();
        request += "&tax_amount=" + info.getTax_amount();
        request += "&fee_shipping=" + info.getFee_shipping();
        request += "&discount_amount=" + info.getDiscount_amount();
        request += "&return_url=" + info.getReturn_url();
        request += "&cancel_url=" + info.getCancel_url();
        request += "&buyer_fullname=" + info.getBuyer_fullname();
        request += "&buyer_email=" + info.getBuyer_email();
        request += "&buyer_mobile=" + info.getBuyer_mobile();
        return request;
    }

    private static String CreateMD5Hash(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(s.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static String GetErrorMessage(String _ErrorCode) {
        String _Message = "";
        switch (_ErrorCode) {
            case "00":
                _Message = "Giao d???ch th??nh c??ng";
                break;
            case "01":
                _Message = "L???i, ?????a ch??? IP truy c???p API c???a Ng??nL?????ng.vn b??? t??? ch???i";
                break;
            case "02":
                _Message = "L???i, tham s??? g???i t??? merchant t???i Ng??nL?????ng.vn ch??a ch??nh x??c.";
                break;
            case "03":
                _Message = "L???i, m?? merchant kh??ng t???n t???i ho???c merchant ??ang b??? kh??a k???t n???i t???i Ng??nL?????ng.vn";
                break;
            case "04":
                _Message = "L???i, m?? checksum kh??ng ch??nh x??c";
                break;
            case "05":
                _Message = "T??i kho???n nh???n ti???n n???p c???a merchant kh??ng t???n t???i";
                break;
            case "06":
                _Message = "T??i kho???n nh???n ti???n n???p c???a  merchant ??ang b??? kh??a ho???c b??? phong t???a, kh??ng th??? th???c hi???n ???????c giao d???ch n???p ti???n";
                break;
            case "07":
                _Message = "Th??? ???? ???????c s??? d???ng";
                break;
            case "08":
                _Message = "Th??? b??? kh??a";
                break;
            case "09":
                _Message = "Th??? h???t h???n s??? d???ng";
                break;
            case "10":
                _Message = "Th??? ch??a ???????c k??ch ho???t ho???c kh??ng t???n t???i";
                break;
            case "11":
                _Message = "M?? th??? sai ?????nh d???ng";
                break;
            case "12":
                _Message = "Sai s??? serial c???a th???";
                break;
            case "13":
                _Message = "M?? th??? v?? s??? serial kh??ng kh???p";
                break;
            case "14":
                _Message = "Th??? kh??ng t???n t???i";
                break;
            case "15":
                _Message = "Th??? kh??ng s??? d???ng ???????c";
                break;
            case "16":
                _Message = "S??? l???n t????? c???a th??? v?????t qu?? gi???i h???n cho ph??p";
                break;
            case "17":
                _Message = "H??? th???ng Telco b??? l???i ho???c qu?? t???i, th??? ch??a b??? tr???";
                break;
            case "18":
                _Message = "H??? th???ng Telco  b??? l???i ho???c qu?? t???i, th??? c?? th??? b??? tr???, c???n ph???i h???p v???i nh?? m???ng ????? ?????i so??t";
                break;
            case "19":
                _Message = "K???t n???i Ng??nL?????ng v???i Telco b??? l???i, th??? ch??a b??? tr???.";
                break;
            case "20":
                _Message = "K???t n???i t???i Telco th??nh c??ng, th??? b??? tr??? nh??ng ch??a c???ng ti???n tr??n Ng??nL?????ng.vn";
                break;
            case "99":
                _Message = "L???i tuy nhi??n l???i ch??a ???????c ?????nh ngh??a ho???c ch??a x??c ?????nh ???????c nguy??n nh??n";
                break;
        }
        return _Message;
    }
}
