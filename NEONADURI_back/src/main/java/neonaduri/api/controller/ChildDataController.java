package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.repository.ClassificationRepository;
import neonaduri.api.repository.RegionRepository;
import neonaduri.domain.Classification;
import neonaduri.domain.Region;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.JMException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChildDataController {

    private final RegionRepository regionRepository;

    private final ClassificationRepository classificationRepository;

    /**
     * child data 주입
     */
    @GetMapping("/child")
    @Transactional
    public void childFacilityData() {

        BufferedReader br = null;

        try {
            //파일 넣어준다.
            br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\SSAFY\\Desktop\\neonaduri\\S07P22A702\\data\\childFacility.csv"), "UTF-8"));
            String line = "";
            br.readLine(); //첫 행은 버린다. (컬럼명이므로)

            //파일이 끝날 때까지 돌린다.
            while ((line = br.readLine()) != null) {
                /*
                  *을 기준으로 찢어서 배열에 담아준다.
                  array[0] : 시설 명
                  array[1] : 주소
                  array[2] : 전화번호
                  array[3] : 위도
                  array[4] : 경도
                 */
                String[] array = line.split("\\*");

/** regionId 뽑기 */
//                주소를 띄어쓰기 기준으로 쪼갠다.
//                2번 인덱스가 시/군/구로 끝난다면 시군구가 두 단어, 3번 인덱스가 면
//                그렇지 않다면 2번 인덱스가 면
                String[] address = array[1].split(" ");

                String sido = null;
                String[] sidoList = {"세종특별자치시", "제주특별자치도", "경기도", "강원도", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "충청북도", "충청남도", "경상북도", "경상남도", "전라북도", "전라남도"};

                //시도명 올바르게 넣어줌
                if (address[0].equals("서울")) {
                    sido = "서울특별시";
                } else if (address[0].equals("부산") || address[0].equals("대구") || address[0].equals("인천") || address[0].equals("광주") || address[0].equals("대전") || address[0].equals("울산")) {
                    sido = address[0] + "광역시";
                }
//                else if (address[0].equals("세종특별자치시") || address[0].equals("제주특별자치도") || address[0].equals("경기도") || address[0].equals("강원도")
//                        || address[0].equals("부산광역시") || address[0].equals("대구광역시") || address[0].equals("인천광역시") || address[0].equals("광주광역시")
//                        || address[0].equals("대전광역시") || address[0].equals("울산광역시") || address[0].equals("충청북도") || address[0].equals("충청남도")
//                        || address[0].equals("경상북도") || address[0].equals("경상남도") || address[0].equals("전라북도") || address[0].equals("전라남도")) {
//                    sido = address[0];
//                }
                else if (Arrays.asList(sidoList).contains(address[0])) {
                    sido = address[0];
                }
                else if (address[0].equals("경기") || address[0].equals("강원")) {
                    sido = address[0] + "도";
                } else if (address[0].equals("충북")) {
                    sido = "충청북도";
                } else if (address[0].equals("충남")) {
                    sido = "충청남도";
                } else if (address[0].equals("경북")) {
                    sido = "경상북도";
                } else if (address[0].equals("경남")) {
                    sido = "경상남도";
                } else if (address[0].equals("전북")) {
                    sido = "전라북도";
                } else if (address[0].equals("전남")) {
                    sido = "전라남도";
                }


                String sigungu = null;
                String myeon = null;

                String check = address[2]; //사용하기 쉽게 check 이라는 변수에 담아준다.

                //세종특별자치시일 경우 (데이터에 시군구 내용 없음)
                if (address[0].charAt(0) == '세') {
                    sigungu = "세종시";
                    myeon = address[1];
                }
                //세종시가 아닌 경우
                else {
                    //check가 시군구라면 check 버립니다.
                    if (check.charAt(check.length() - 1) == '시' || check.charAt(check.length() - 1) == '군' || check.charAt(check.length() - 1) == '구') {
                        sigungu = address[1];
                        myeon = address[3];
                    }
                    //check가 시군구가 아니라면 check가 면
                    else {
                        sigungu = address[1];
                        myeon = address[2];
                    }
                }

                //용담1동, 용담2동, 용담3동, 일도일동, 일도이동
                if(myeon.contains("용담")){
                    if (myeon.contains("1")){
                        myeon = "용담일동";
                    }else if (myeon.contains("2")){
                        myeon = "용담이동";
                    }else if (myeon.contains("3")){
                        myeon = "용담삼동";
                    }
                }else if (myeon.contains("일도")){
                    if(myeon.contains("1")){
                        myeon = "일도일동";
                    } else if (myeon.contains("2")){
                        myeon = "일도이동";
                    }
                }


                System.out.println("시도 : " + sido);
                System.out.println("시군구 : " + sigungu);
                System.out.println("면 : " + myeon);

                //추출한 시군구, 면으로 지역코드 뽑아낸다.
                Region region = regionRepository.findRegionBySidoAndSigunguAndMyeon(sido, sigungu, myeon);
                Long regionId = region.getRegionId();
                System.out.println(regionId);

/** classification 뽑기*/
                //spotName 이 지정된 캐릭터명을 포함하는지 확인하여 분류
                //뽀로로, 타요, 폴리, 라바, 기타
                Classification cls;

                if(array[0].contains("뽀로로")){
                    cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "뽀로로");
                } else if(array[0].contains("타요")){
                     cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "타요");
                } else if(array[0].contains("폴리")){
                     cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "폴리");
                } else if(array[0].contains("라바")){
                     cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "라바");
                } else {
                     cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "기타");
                }

                Long clsId = cls.getClassId();

                System.out.println(clsId);


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //스트림 닫아준다.
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
