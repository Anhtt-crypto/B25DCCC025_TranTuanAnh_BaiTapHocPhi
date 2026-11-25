import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface MienGiam {
    long tinhGiam(long hocPhi);
}

class ConChinhSach implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.round(hocPhi * 0.5);
    }
}

class HoNgheo implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.round(hocPhi * 0.7);
    }
}

class HocBong implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(3000000L, hocPhi);
    }
}

abstract class SinhVien {
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected MienGiam chinhSach;
    protected static final long DON_GIA = 450000L;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
        this.chinhSach = null;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getMaHe() {
        return maHe;
    }

    public void setChinhSach(MienGiam chinhSach) {
        this.chinhSach = chinhSach;
    }

    public abstract long hocPhi();

    public long tienGiam() {
        if (chinhSach == null) return 0;
        return chinhSach.tinhGiam(hocPhi());
    }

    public long phaiNop() {
        return hocPhi() - tienGiam();
    }

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi() + " " + tienGiam() + " " + phaiNop();
    }
}

class SinhVienCQ extends SinhVien {
    public SinhVienCQ(String maSV, int soTinChi) {
        super(maSV, "CQ", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA;
    }
}

class SinhVienCLC extends SinhVien {
    public SinhVienCLC(String maSV, int soTinChi) {
        super(maSV, "CLC", soTinChi);
    }

    @Override
    public long hocPhi() {
        return Math.round(soTinChi * DON_GIA * 1.5);
    }
}

class SinhVienLT extends SinhVien {
    public SinhVienLT(String maSV, int soTinChi) {
        super(maSV, "LT", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA + 2000000L;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        Map<String, SinhVien> mapSV = new LinkedHashMap<>();
        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            int soTinChi = sc.nextInt();

            SinhVien sv = null;
            switch (maHe) {
                case "CQ":
                    sv = new SinhVienCQ(maSV, soTinChi);
                    break;
                case "CLC":
                    sv = new SinhVienCLC(maSV, soTinChi);
                    break;
                case "LT":
                    sv = new SinhVienLT(maSV, soTinChi);
                    break;
            }
            if (sv != null) {
                mapSV.put(maSV, sv);
            }
        }

        int m = sc.hasNextInt() ? sc.nextInt() : 0;
        List<String> notFoundList = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            String maSV = sc.next();
            String maCS = sc.next();

            SinhVien sv = mapSV.get(maSV);
            if (sv == null) {
                notFoundList.add("NOT FOUND " + maSV);
            } else {
                switch (maCS) {
                    case "CS":
                        sv.setChinhSach(new ConChinhSach());
                        break;
                    case "HN":
                        sv.setChinhSach(new HoNgheo());
                        break;
                    case "HB":
                        sv.setChinhSach(new HocBong());
                        break;
                }
            }
        }

        for (String nf : notFoundList) {
            System.out.println(nf);
        }

        for (SinhVien sv : mapSV.values()) {
            System.out.println(sv);
        }

        String[] cacHe = {"CQ", "CLC", "LT"};
        for (String he : cacHe) {
            int count = 0;
            long tongPhaiNop = 0;
            for (SinhVien sv : mapSV.values()) {
                if (sv.getMaHe().equals(he)) {
                    count++;
                    tongPhaiNop += sv.phaiNop();
                }
            }
            if (count > 0) {
                System.out.println(he + " " + count + " " + tongPhaiNop);
            }
        }
        sc.close();
    }
}