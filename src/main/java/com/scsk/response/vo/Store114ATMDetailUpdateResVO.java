package com.scsk.response.vo;

import com.scsk.constants.Constants;
import com.scsk.model.geo.GeometryDoc;

public class Store114ATMDetailUpdateResVO extends BaseResVO{

    private Boolean select;
    private String _id;
    private String _rev;
    private String type=Constants.FEATURE; 
    private GeometryDoc geometry;
    private String docType = Constants.STOREATM_DOCTYPE;
    private int typeKbn;
    private String area;
    private String storeNumber;
    private String storeName;
    private String atmAddress;
    private String manageStore;
    private String postCode;
    private String address;
    private String teleNumber;
    private String serviceConversionIn;
    private String serviceConversionOut;
    private String trustAgent;
    private String policeCompany;
    private String conversionMachine;
    private String accountMachine;
    private String loanMachine;
    private String storeOpenStartTime;
    private String storeOpenEndTime;
    private String storeOpenStartTime_SAT;
    private String storeOpenEndTime_SAT;
    private String storeOpenStartTime_SUN;
    private String storeOpenEndTime_SUN;
    private String atmOpenStartTime;
    private String atmOpenEndTime;
    private String atmOpenStartTime_SAT;
    private String atmOpenEndTime_SAT;
    private String atmOpenStartTime_SUN;
    private String atmOpenEndTime_SUN;
    private String storeOpenStartHour;
    private String storeOpenEndHour;
    private String storeOpenStartHour_SAT;
    private String storeOpenEndHour_SAT;
    private String storeOpenStartHour_SUN;
    private String storeOpenEndHour_SUN;
    private String atmOpenStartHour;
    private String atmOpenEndHour;
    private String atmOpenStartHour_SAT;
    private String atmOpenEndHour_SAT;
    private String atmOpenStartHour_SUN;
    private String atmOpenEndHour_SUN;
    private String storeOpenStartMinute;
    private String storeOpenEndMinute;
    private String storeOpenStartMinute_SAT;
    private String storeOpenEndMinute_SAT;
    private String storeOpenStartMinute_SUN;
    private String storeOpenEndMinute_SUN;
    private String atmOpenStartMinute;
    private String atmOpenEndMinute;
    private String atmOpenStartMinute_SAT;
    private String atmOpenEndMinute_SAT;
    private String atmOpenStartMinute_SUN;
    private String atmOpenEndMinute_SUN;
    private String latitude;
    private String longitude;
    private String delFlg;
    
    public String getStoreOpenStartHour() {
        return storeOpenStartHour;
    }
    public void setStoreOpenStartHour(String storeOpenStartHour) {
        this.storeOpenStartHour = storeOpenStartHour;
    }
    public String getStoreOpenEndHour() {
        return storeOpenEndHour;
    }
    public void setStoreOpenEndHour(String storeOpenEndHour) {
        this.storeOpenEndHour = storeOpenEndHour;
    }
    public String getStoreOpenStartHour_SAT() {
        return storeOpenStartHour_SAT;
    }
    public void setStoreOpenStartHour_SAT(String storeOpenStartHour_SAT) {
        this.storeOpenStartHour_SAT = storeOpenStartHour_SAT;
    }
    public String getStoreOpenEndHour_SAT() {
        return storeOpenEndHour_SAT;
    }
    public void setStoreOpenEndHour_SAT(String storeOpenEndHour_SAT) {
        this.storeOpenEndHour_SAT = storeOpenEndHour_SAT;
    }
    public String getStoreOpenStartHour_SUN() {
        return storeOpenStartHour_SUN;
    }
    public void setStoreOpenStartHour_SUN(String storeOpenStartHour_SUN) {
        this.storeOpenStartHour_SUN = storeOpenStartHour_SUN;
    }
    public String getStoreOpenEndHour_SUN() {
        return storeOpenEndHour_SUN;
    }
    public void setStoreOpenEndHour_SUN(String storeOpenEndHour_SUN) {
        this.storeOpenEndHour_SUN = storeOpenEndHour_SUN;
    }
    public String getAtmOpenStartHour() {
        return atmOpenStartHour;
    }
    public void setAtmOpenStartHour(String atmOpenStartHour) {
        this.atmOpenStartHour = atmOpenStartHour;
    }
    public String getAtmOpenEndHour() {
        return atmOpenEndHour;
    }
    public void setAtmOpenEndHour(String atmOpenEndHour) {
        this.atmOpenEndHour = atmOpenEndHour;
    }
    public String getAtmOpenStartHour_SAT() {
        return atmOpenStartHour_SAT;
    }
    public void setAtmOpenStartHour_SAT(String atmOpenStartHour_SAT) {
        this.atmOpenStartHour_SAT = atmOpenStartHour_SAT;
    }
    public String getAtmOpenEndHour_SAT() {
        return atmOpenEndHour_SAT;
    }
    public void setAtmOpenEndHour_SAT(String atmOpenEndHour_SAT) {
        this.atmOpenEndHour_SAT = atmOpenEndHour_SAT;
    }
    public String getAtmOpenStartHour_SUN() {
        return atmOpenStartHour_SUN;
    }
    public void setAtmOpenStartHour_SUN(String atmOpenStartHour_SUN) {
        this.atmOpenStartHour_SUN = atmOpenStartHour_SUN;
    }
    public String getAtmOpenEndHour_SUN() {
        return atmOpenEndHour_SUN;
    }
    public void setAtmOpenEndHour_SUN(String atmOpenEndHour_SUN) {
        this.atmOpenEndHour_SUN = atmOpenEndHour_SUN;
    }
    public String getStoreOpenStartMinute() {
        return storeOpenStartMinute;
    }
    public void setStoreOpenStartMinute(String storeOpenStartMinute) {
        this.storeOpenStartMinute = storeOpenStartMinute;
    }
    public String getStoreOpenEndMinute() {
        return storeOpenEndMinute;
    }
    public void setStoreOpenEndMinute(String storeOpenEndMinute) {
        this.storeOpenEndMinute = storeOpenEndMinute;
    }
    public String getStoreOpenStartMinute_SAT() {
        return storeOpenStartMinute_SAT;
    }
    public void setStoreOpenStartMinute_SAT(String storeOpenStartMinute_SAT) {
        this.storeOpenStartMinute_SAT = storeOpenStartMinute_SAT;
    }
    public String getStoreOpenEndMinute_SAT() {
        return storeOpenEndMinute_SAT;
    }
    public void setStoreOpenEndMinute_SAT(String storeOpenEndMinute_SAT) {
        this.storeOpenEndMinute_SAT = storeOpenEndMinute_SAT;
    }
    public String getStoreOpenStartMinute_SUN() {
        return storeOpenStartMinute_SUN;
    }
    public void setStoreOpenStartMinute_SUN(String storeOpenStartMinute_SUN) {
        this.storeOpenStartMinute_SUN = storeOpenStartMinute_SUN;
    }
    public String getStoreOpenEndMinute_SUN() {
        return storeOpenEndMinute_SUN;
    }
    public void setStoreOpenEndMinute_SUN(String storeOpenEndMinute_SUN) {
        this.storeOpenEndMinute_SUN = storeOpenEndMinute_SUN;
    }
    public String getAtmOpenStartMinute() {
        return atmOpenStartMinute;
    }
    public void setAtmOpenStartMinute(String atmOpenStartMinute) {
        this.atmOpenStartMinute = atmOpenStartMinute;
    }
    public String getAtmOpenEndMinute() {
        return atmOpenEndMinute;
    }
    public void setAtmOpenEndMinute(String atmOpenEndMinute) {
        this.atmOpenEndMinute = atmOpenEndMinute;
    }
    public String getAtmOpenStartMinute_SAT() {
        return atmOpenStartMinute_SAT;
    }
    public void setAtmOpenStartMinute_SAT(String atmOpenStartMinute_SAT) {
        this.atmOpenStartMinute_SAT = atmOpenStartMinute_SAT;
    }
    public String getAtmOpenEndMinute_SAT() {
        return atmOpenEndMinute_SAT;
    }
    public void setAtmOpenEndMinute_SAT(String atmOpenEndMinute_SAT) {
        this.atmOpenEndMinute_SAT = atmOpenEndMinute_SAT;
    }
    public String getAtmOpenStartMinute_SUN() {
        return atmOpenStartMinute_SUN;
    }
    public void setAtmOpenStartMinute_SUN(String atmOpenStartMinute_SUN) {
        this.atmOpenStartMinute_SUN = atmOpenStartMinute_SUN;
    }
    public String getAtmOpenEndMinute_SUN() {
        return atmOpenEndMinute_SUN;
    }
    public void setAtmOpenEndMinute_SUN(String atmOpenEndMinute_SUN) {
        this.atmOpenEndMinute_SUN = atmOpenEndMinute_SUN;
    }
    public String getDelFlg() {
        return delFlg;
    }
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public GeometryDoc getGeometry() {
        return geometry;
    }
    public void setGeometry(GeometryDoc geometry) {
        this.geometry = geometry;
    }
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }

    public int getTypeKbn() {
        return typeKbn;
    }
    public void setTypeKbn(int typeKbn) {
        this.typeKbn = typeKbn;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getStoreNumber() {
        return storeNumber;
    }
    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAtmAddress() {
        return atmAddress;
    }
    public void setAtmAddress(String atmAddress) {
        this.atmAddress = atmAddress;
    }
    public String getManageStore() {
        return manageStore;
    }
    public void setManageStore(String manageStore) {
        this.manageStore = manageStore;
    }
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTeleNumber() {
        return teleNumber;
    }
    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }
    public String getServiceConversionIn() {
        return serviceConversionIn;
    }
    public void setServiceConversionIn(String serviceConversionIn) {
        this.serviceConversionIn = serviceConversionIn;
    }
    public String getServiceConversionOut() {
        return serviceConversionOut;
    }
    public void setServiceConversionOut(String serviceConversionOut) {
        this.serviceConversionOut = serviceConversionOut;
    }
    public String getTrustAgent() {
        return trustAgent;
    }
    public void setTrustAgent(String trustAgent) {
        this.trustAgent = trustAgent;
    }
    public String getPoliceCompany() {
        return policeCompany;
    }
    public void setPoliceCompany(String policeCompany) {
        this.policeCompany = policeCompany;
    }
    public String getConversionMachine() {
        return conversionMachine;
    }
    public void setConversionMachine(String conversionMachine) {
        this.conversionMachine = conversionMachine;
    }
    public String getAccountMachine() {
        return accountMachine;
    }
    public void setAccountMachine(String accountMachine) {
        this.accountMachine = accountMachine;
    }
    public String getLoanMachine() {
        return loanMachine;
    }
    public void setLoanMachine(String loanMachine) {
        this.loanMachine = loanMachine;
    }
    public String getStoreOpenStartTime() {
        return storeOpenStartTime;
    }
    public void setStoreOpenStartTime(String storeOpenStartTime) {
        this.storeOpenStartTime = storeOpenStartTime;
    }
    public String getStoreOpenEndTime() {
        return storeOpenEndTime;
    }
    public void setStoreOpenEndTime(String storeOpenEndTime) {
        this.storeOpenEndTime = storeOpenEndTime;
    }
    public String getStoreOpenStartTime_SAT() {
        return storeOpenStartTime_SAT;
    }
    public void setStoreOpenStartTime_SAT(String storeOpenStartTime_SAT) {
        this.storeOpenStartTime_SAT = storeOpenStartTime_SAT;
    }
    public String getStoreOpenEndTime_SAT() {
        return storeOpenEndTime_SAT;
    }
    public void setStoreOpenEndTime_SAT(String storeOpenEndTime_SAT) {
        this.storeOpenEndTime_SAT = storeOpenEndTime_SAT;
    }
    public String getStoreOpenStartTime_SUN() {
        return storeOpenStartTime_SUN;
    }
    public void setStoreOpenStartTime_SUN(String storeOpenStartTime_SUN) {
        this.storeOpenStartTime_SUN = storeOpenStartTime_SUN;
    }
    public String getStoreOpenEndTime_SUN() {
        return storeOpenEndTime_SUN;
    }
    public void setStoreOpenEndTime_SUN(String storeOpenEndTime_SUN) {
        this.storeOpenEndTime_SUN = storeOpenEndTime_SUN;
    }
    public String getAtmOpenStartTime() {
        return atmOpenStartTime;
    }
    public void setAtmOpenStartTime(String atmOpenStartTime) {
        this.atmOpenStartTime = atmOpenStartTime;
    }
    public String getAtmOpenEndTime() {
        return atmOpenEndTime;
    }
    public void setAtmOpenEndTime(String atmOpenEndTime) {
        this.atmOpenEndTime = atmOpenEndTime;
    }
    public String getAtmOpenStartTime_SAT() {
        return atmOpenStartTime_SAT;
    }
    public void setAtmOpenStartTime_SAT(String atmOpenStartTime_SAT) {
        this.atmOpenStartTime_SAT = atmOpenStartTime_SAT;
    }
    public String getAtmOpenEndTime_SAT() {
        return atmOpenEndTime_SAT;
    }
    public void setAtmOpenEndTime_SAT(String atmOpenEndTime_SAT) {
        this.atmOpenEndTime_SAT = atmOpenEndTime_SAT;
    }
    public String getAtmOpenStartTime_SUN() {
        return atmOpenStartTime_SUN;
    }
    public void setAtmOpenStartTime_SUN(String atmOpenStartTime_SUN) {
        this.atmOpenStartTime_SUN = atmOpenStartTime_SUN;
    }
    public String getAtmOpenEndTime_SUN() {
        return atmOpenEndTime_SUN;
    }
    public void setAtmOpenEndTime_SUN(String atmOpenEndTime_SUN) {
        this.atmOpenEndTime_SUN = atmOpenEndTime_SUN;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public Boolean getSelect() {
        return select;
    }
    public void setSelect(Boolean select) {
        this.select = select;
    }
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String get_rev() {
        return _rev;
    }
    public void set_rev(String _rev) {
        this._rev = _rev;
    }


}
