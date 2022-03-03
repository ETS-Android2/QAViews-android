package com.qdegrees.qaudittool.common;

public class Url {

    private static final String ROOT_URL = "https://mamamoney.qaviews.com/api/";

    public static final String LOGIN_USER = ROOT_URL + "login";
    public static final String AUDIO_FILE_LIST = ROOT_URL + "recording_list";
    public static final String FILTER_LIST = ROOT_URL + "listenning_call_filters";
    public static final String SEARCHING = ROOT_URL + "searching";


    public static final String FEEDBACK_NOTIFICATION = ROOT_URL + "notification_feedback";
    public static final String PARAMETER_WISE_SCORE = ROOT_URL + "parameter_wise_details";
    public static final String RAISE_REBUTTAL = ROOT_URL + "Rebuttal";




    // Agent Feedback
    public static final String AUDIT_ACCEPT = ROOT_URL + "accepted";






    // API for Auditor
    public static final String AUDITOR_DASHBOARD = ROOT_URL + "auditor_dashboard";
    public static final String AUDITOR_DASHBOARD_PROCESS_NAME = ROOT_URL + "auditor_process_list";
    public static final String AUDITOR_AGENT_NAME = ROOT_URL + "audit_page_render_sheet";
    public static final String AUDITOR_SUBMITTED_AUDIT_LIST = ROOT_URL + "audited_main_pool";



    // for Stock Management
    public static final String IN_STOCK_URL = ROOT_URL + "inStockDevice";
    public static final String ALL_IMEI_NUMBER = ROOT_URL + "allDevices";
    public static final String OUT_STOCK_URL = ROOT_URL + "outStockDevice";

    public static final String EXTRACT_DEVICE = ROOT_URL + "saveExtraction";
    public static final String SUBMIT_DEVICE = ROOT_URL + "submitDevice";
    public static final String EXTRACT_DEVICE_BY_ME = ROOT_URL + "myAllocatedDevices";
    public static final String SUBMIT_DEVICE_BY_ME = ROOT_URL + "mySubmittedDevices";

    public static final String PLANT_IN_STOCK = ROOT_URL + "myPlantInStock";
    public static final String PLANT_OUT_STOCK = ROOT_URL + "myPlantOutStock";


    public static final String USER_NAME = ROOT_URL + "getUserHandoverTo";


    /// for JKLC
    public static final String JK_DAILY_CALL_REPORT = ROOT_URL + "jkDailyReportCreate";
    public static final String JK_SHIFT_HANDOVER = ROOT_URL + "handover";
    public static final String JK_SHIFTHANDOVER_PENDING = ROOT_URL + "lastHandoverRecived";
    public static final String JK_SHIFTHANDOVER_STATUS = ROOT_URL + "handOverAction";

    // for Job Slip
    public static final String ALL_TECHNICIAN = ROOT_URL + "get_technitians";
    public static final String NEW_JOB = ROOT_URL + "assign_job";
    public static final String OFFICE_GUY_PENDING = ROOT_URL + "get_office_guy_pending";
    public static final String OFFICE_GUY_DONE = ROOT_URL + "get_office_guy_completed";
    public static final String TECHNICIAN_PENDING_JOB = ROOT_URL + "get_technitian_guy_pending";
    public static final String PERFORM_JOB_OPERATION = ROOT_URL + "perform_job";
    public static final String TECH_COMPLETED_JOB = ROOT_URL + "get_technitian_guy_completed";

    // for Inventory Management
    public static final String RUNNING_DEVICE = ROOT_URL + "get_running_devices";
    public static final String SEARCH_RUNNING_DEVICE = ROOT_URL + "search_vehicle";

    public static final String FAREYE_ALL_WORKING_DEVICE = ROOT_URL + "get_fareye_vehicle_with_working_devices";
    public static final String FAREYE_ALL_NOTWORKING_DEVICE = ROOT_URL + "get_fareye_vehicle_with_not_working_devices";

    public static final String JKLC_ALL_WORKING_DEVICE = ROOT_URL + "get_jklc_vehicle_with_working_devices";
    public static final String JKLC_ALL_NOTWORKING_DEVICE = ROOT_URL + "get_jklc_vehicle_with_not_working_devices";

    public static final String ADD_NEW_VEHICLE = ROOT_URL + "add_new_vehicle";
    public static final String OFFICE_ALL_WORKING_IMEI = ROOT_URL + "get_all_working_devices";

    public static final String SEND_LAT_LOG = ROOT_URL + "login_attempt_with_geo_location";

    public static final String ALL_PLANT_NAME = ROOT_URL + "all_plant_name";
    public static final String MOVE_IMEI_PLANT = ROOT_URL + "change_plant_of_device";

    public static final String CHANGE_SECURITY_PIN = ROOT_URL + "change_security_pin";
    public static final String VEHICLE_DETAILS = ROOT_URL + "vehicle_details";

    public static final String HZL_INCIDENT_REPORT = ROOT_URL + "hzl_incident";


    // New Changes API
    public static final String COMPANY_PLANT_NAME = ROOT_URL + "plant_name_company";
    public static final String WORKING_DEVICE_LIST_MAP_WITH_USER = ROOT_URL + "get_all_device_map_user_plant_working";
    public static final String NOTWORKING_DEVICE_LIST_MAP_WITH_USER = ROOT_URL + "get_all_device_map_user_plant_not_working";
    public static final String ALL_TRANSIT_DEVICE = ROOT_URL + "get_all_transit_device";
    public static final String CHANGE_PLANT_TRANSIT_DEVICE = ROOT_URL + "change_plant_of_transit_device";
    public static final String JOB_EDIT = ROOT_URL + "update_created_job";
    public static final String JKLC_NOT_WORKING = ROOT_URL + "get_jklc_notworking_devices";
    public static final String HZL_NOT_WORKING = ROOT_URL + "get_hzl_notworking_devices";
    public static final String CHANGE_DEVICE_STATUS = ROOT_URL + "change_status_of_device";
    public static final String PUNCH_ATTENDANCE = ROOT_URL + "save_punch_time";
    public static final String WORKING_TIME_PUNCH = ROOT_URL + "save_working_time_location";


    public static final String PENDING_APPROVAL = ROOT_URL + "show_approval_pending_plant_wise";
    public static final String PENDING_APPROVAL_MOVE = ROOT_URL + "change_plant_of_device_approval";
    public static final String TRANSIT_DEVICE = ROOT_URL + "approval_transit_device";



    public static final String JKLC_WORKING = ROOT_URL + "get_jklc_working_devices";
    public static final String HZL_WORKING = ROOT_URL + "get_hzl_working_devices";

}