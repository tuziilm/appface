package com.zhanghui.appface.service;

import com.google.common.collect.Sets;
import com.zhanghui.appface.common.IpSeeker;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedirectService {
    public final String COOL123_URL="http://m.yoyogame.net/callback/link_node/lnc-cee5fb4837bd4e69a57a90e777009c6e";
    //���� ���� ��� ���ô� ��ʿ �Ĵ����� Ӣ�� ���� �¼��� ���� Ų�� ����� �¹� ������ ������ ������ �ձ� ɳ�ذ�����
    // ̨�� ̩�� ӡ�� ������ ���� ���� ������ ������˹̹ ӡ�������� �ڿ��� ¬ɭ�� �������� ī���� �й� ��� ����
    public final Set<String> NOT_COOL123_COUNTRIES= Sets.newHashSet("fi","us","se","ca","au","gb","nl","sg",
            "fr","no","it","de","nz","kw","jp","sa","th","in","ie","br","kr","tr","kz","id","ua","mk",
            "my","mx","tw","cn","hk","mo"
    );
    public final String BR_WHEREISMYAPPS_URL="http://m.yoyogame.net/callback/link_node/lnc-b86e5c87836247fdaf809f3135c451dd";
    public final String TH_WHEREISMYAPPS_URL="http://m.yoyogame.net/callback/link_node/lnc-fab3747567994940b868e927d4e37024";

    public String redirect(IpSeeker.IpData ipData){
        if(ipData==null){
            return null;
        }
        if("br".equals(ipData.shortcut)){
            return BR_WHEREISMYAPPS_URL;
        }
        if("th".equals(ipData.shortcut)){
            return TH_WHEREISMYAPPS_URL;
        }
        if(NOT_COOL123_COUNTRIES.contains(ipData.shortcut)){
            return null;
        }
        return COOL123_URL;
    }
}
