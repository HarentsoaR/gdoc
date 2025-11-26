package mg.md2i.gedi.control.viewmodel;

import mg.md2i.gedi.entity.Connexion1;
import mg.md2i.gedi.entity.Historique;
import mg.md2i.gedi.entity.revinfo;
import mg.md2i.gedi.gestionmetier.Connexion1Gestion;
import mg.md2i.gedi.gestionmetier.HistoriqueGestion;
import mg.md2i.gedi.gestionmetier.RevinfoGestion;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AuditViewModel {

    private Date fromDate;
    private Date toDate;
    private String searchOperation = "";
    private Integer selectedUserId;
    private String keyword = "";

    private List<ConnexionRow> connexions = new ArrayList<>();
    private List<HistoriqueRow> historiques = new ArrayList<>();
    private List<RevisionRow> revisions = new ArrayList<>();

    @Init
    public void init() {
        loadData();
    }

    @Command
    @NotifyChange({"connexions", "historiques", "revisions"})
    public void filter() {
        loadData();
    }

    @Command
    @NotifyChange({"connexions", "historiques", "revisions"})
    public void reset() {
        fromDate = null;
        toDate = null;
        searchOperation = "";
        selectedUserId = null;
        keyword = "";
        loadData();
    }

    private void loadData() {
        List<Connexion1> cx;
        if (fromDate != null && toDate != null) {
            cx = Connexion1Gestion.findByDateRange(fromDate, toDate);
        } else {
            cx = Connexion1Gestion.findAllActive();
        }

        if (selectedUserId != null) {
            cx = cx.stream()
                    .filter(c -> selectedUserId.equals(c.getUtilisateurId()))
                    .collect(Collectors.toList());
        }
        connexions = cx.stream()
                .map(ConnexionRow::new)
                .filter(row -> matchesKeyword(row.getSearchableText(), keyword))
                .collect(Collectors.toList());

        List<Historique> histo;
        if (fromDate != null && toDate != null) {
            histo = HistoriqueGestion.findByDateRange(fromDate, toDate);
        } else {
            histo = HistoriqueGestion.findAll();
        }
        if (searchOperation != null && !searchOperation.trim().isEmpty()) {
            String op = searchOperation.trim();
            histo = histo.stream()
                    .filter(h -> h.getOperation() != null && h.getOperation().toLowerCase(Locale.ROOT).contains(op.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        historiques = histo.stream()
                .map(HistoriqueRow::new)
                .filter(row -> matchesKeyword(row.getSearchableText(), keyword))
                .collect(Collectors.toList());

        revisions = RevinfoGestion.findAllActive().stream()
                .map(RevisionRow::new)
                .collect(Collectors.toList());
    }

    public List<ConnexionRow> getConnexions() {
        return connexions;
    }

    public List<HistoriqueRow> getHistoriques() {
        return historiques;
    }

    public List<RevisionRow> getRevisions() {
        return revisions;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getSearchOperation() {
        return searchOperation;
    }

    public void setSearchOperation(String searchOperation) {
        this.searchOperation = searchOperation;
    }

    public Integer getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Integer selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getConnexionCount() {
        return connexions != null ? connexions.size() : 0;
    }

    public int getHistoriqueCount() {
        return historiques != null ? historiques.size() : 0;
    }

    private boolean matchesKeyword(String haystack, String needle) {
        if (needle == null || needle.trim().isEmpty()) {
            return true;
        }
        return haystack != null && haystack.toLowerCase(Locale.ROOT).contains(needle.trim().toLowerCase(Locale.ROOT));
    }

    public static class ConnexionRow {
        private final Connexion1 cx;
        private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        public ConnexionRow(Connexion1 cx) {
            this.cx = cx;
        }

        public String getUtilisateur() {
            if (cx.getUtilisateur() != null) {
                return cx.getUtilisateur().getLogin();
            }
            return String.valueOf(cx.getUtilisateurId());
        }

        public String getDateDebut() {
            return cx.getDateDebut() != null ? FORMAT.format(cx.getDateDebut()) : "-";
        }

        public String getAdresseIp() {
            return cx.getAdresseIp();
        }

        public String getNavigateur() {
            return cx.getNavigateur();
        }

        public String getOrdinateur() {
            return cx.getOrdinateur();
        }

        public String getSearchableText() {
            return (getUtilisateur() + " " + getAdresseIp() + " " + getNavigateur() + " " + getOrdinateur()).toLowerCase(Locale.ROOT);
        }
    }

    public static class HistoriqueRow {
        private final Historique h;
        private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        public HistoriqueRow(Historique h) {
            this.h = h;
        }

        public String getDate() {
            return h.getDate() != null ? FORMAT.format(h.getDate()) : "-";
        }

        public String getOperation() {
            return h.getOperation();
        }

        public String getTable() {
            return h.getTable();
        }

        public String getAvant() {
            return h.getAvant();
        }

        public String getApres() {
            return h.getApres();
        }

        public String getAdresseIp() {
            return h.getAdresseIp();
        }

        public String getNavigateur() {
            return h.getNavigateur();
        }

        public String getSearchableText() {
            return (getOperation() + " " + getTable() + " " + getAvant() + " " + getApres() + " " + getAdresseIp() + " " + getNavigateur()).toLowerCase(Locale.ROOT);
        }
    }

    public static class RevisionRow {
        private final revinfo rev;
        private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        public RevisionRow(revinfo rev) {
            this.rev = rev;
        }

        public Integer getRev() {
            return rev.getREV();
        }

        public Integer getId() {
            return rev.getId();
        }

        public String getTimestampLabel() {
            if (rev.getTimestamp() == null) {
                return "-";
            }
            try {
                return FORMAT.format(new Date(rev.getTimestamp()));
            } catch (Exception e) {
                return String.valueOf(rev.getTimestamp());
            }
        }

        public String getUsername() {
            return rev.getUsername();
        }
    }
}
