package com.jkdys.doctor.utils;

import com.jkdys.doctor.R;

/**
 * Created by yanxin on 2018/5/18.
 */

public class BankUtil {

    public static int getBankIconResId(String bankCode) {
        if("ABC".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_nongye_ban;
        } else  if("BOC".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_zhongguo_bank_icon;
        } else  if("BOS".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_shanghai_bank;
        }  else  if("HXB".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_huaxia_bank;
        } else  if("CCB".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_jianshe_bank;
        } else  if("ICBC".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_gongshang_bank_icon;
        } else  if("NBCB".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_ningbo_bank;
        } else  if("CIB".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_xinye_bank;
        } else  if("PSBC".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_youzheng_bank;
        } else  if("CMBC".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_minsheng_bank_icon;
        } else  if("COMM".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_jiaotong_bank;
        } else  if("SZPAB".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_pingan_bank;
        } else  if("CMB".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_zhaoshang_bank_icon;
        } else  if("GDB".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_guangfa_bank;
        } else  if("SPDB".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_pufa_bank;
        } else if("CITIC".equalsIgnoreCase(bankCode)) {
            return R.drawable.mycard_zhongxin_bank;
        }
        return R.drawable.mycard_list_default_bankcard_icon;
    }

    /**
     *
     *
     * [银行ID]						[银行名称]
     62AC744D-FD68-411B-AC87-41985E94D5AF			中国银行
     F124D585-8308-4C2A-9529-330A0E0ECCB7			农业银行
     81A6A602-5BBA-45DE-B6B0-DE8CCBA4493E			建设银行
     8F4225E8-FEFD-429B-95BB-B427ACB22CA8			工商银行
     8334FEF5-69F8-4ED7-8F03-057B80CD6260			中信银行
     7886E2DA-8847-4570-9390-DD1BA8739FD9			招商银行
     *
     *
     * @return
     */
    public static int getBankIconById(String bankId) {
        if("62AC744D-FD68-411B-AC87-41985E94D5AF".equalsIgnoreCase(bankId)) {
            return R.drawable.mycard_zhongguo_bank_icon;
        } else  if("F124D585-8308-4C2A-9529-330A0E0ECCB7".equalsIgnoreCase(bankId)) {
            return R.drawable.mycard_nongye_ban;
        } else  if("81A6A602-5BBA-45DE-B6B0-DE8CCBA4493E".equalsIgnoreCase(bankId)) {
            return R.drawable.mycard_jianshe_bank;
        }  else  if("8F4225E8-FEFD-429B-95BB-B427ACB22CA8".equalsIgnoreCase(bankId)) {
            return R.drawable.mycard_gongshang_bank_icon;
        } else  if("7886E2DA-8847-4570-9390-DD1BA8739FD9".equalsIgnoreCase(bankId)) {
            return R.drawable.mycard_zhaoshang_bank_icon;
        }
        return R.drawable.mycard_list_default_bankcard_icon;
    }

    public static int getBankColorById(String bankId) {
        if("62AC744D-FD68-411B-AC87-41985E94D5AF".equalsIgnoreCase(bankId)) {
            return R.color.colorBankZhongGuo;
        } else  if("F124D585-8308-4C2A-9529-330A0E0ECCB7".equalsIgnoreCase(bankId)) {
            return R.color.colorBankNongYe;
        } else  if("81A6A602-5BBA-45DE-B6B0-DE8CCBA4493E".equalsIgnoreCase(bankId)) {
            return R.color.colorBankJianShe;
        }  else  if("8F4225E8-FEFD-429B-95BB-B427ACB22CA8".equalsIgnoreCase(bankId)) {
            return R.color.colorBankGongShang;
        } else  if("7886E2DA-8847-4570-9390-DD1BA8739FD9".equalsIgnoreCase(bankId)) {
            return R.color.colorBankZhaoShang;
        }
        return R.color.colorBankDefault;
    }

    public static int getBankColor(String bankCode) {
        if ("ABC".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankNongYe;
        } else if ("BOC".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankZhongGuo;
        } else if ("BOS".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankShangHai;
        } else if ("HXB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankHuaXia;
        } else if ("CCB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankJianShe;
        } else if ("ICBC".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankGongShang;
        } else if ("NBCB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankNingBo;
        } else if ("CIB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankXingYe;
        } else if ("PSBC".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankYouZheng;
        } else if ("CMBC".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankMinSheng;
        } else if ("COMM".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankJiaoTong;
        } else if ("SZPAB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankPingAn;
        } else if ("CMB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankZhaoShang;
        } else if ("GDB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankGuangFa;
        } else if ("SPDB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankPuFa;
        } else if ("CITIC".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankZhongXin;
        } else if ("SHRCB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankShangHaiNongShang;
        } else if ("JSCB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankJiangSu;
        } else if ("DLCB".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankDaLian;
        } else if ("ORDOS".equalsIgnoreCase(bankCode)) {
            return R.color.colorBankEErDuoSi;
        }
        return R.color.colorBankDefault;
    }

}
