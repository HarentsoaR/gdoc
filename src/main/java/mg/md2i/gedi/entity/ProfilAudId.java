package mg.md2i.gedi.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite key for ProfilAud (profil_id + REV).
 */
public class ProfilAudId implements Serializable {
    private Integer profilId;
    private Integer REV;

    public ProfilAudId() {
    }

    public ProfilAudId(Integer profilId, Integer REV) {
        this.profilId = profilId;
        this.REV = REV;
    }

    public Integer getProfilId() {
        return profilId;
    }

    public Integer getREV() {
        return REV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfilAudId that = (ProfilAudId) o;
        return Objects.equals(profilId, that.profilId) && Objects.equals(REV, that.REV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profilId, REV);
    }
}
