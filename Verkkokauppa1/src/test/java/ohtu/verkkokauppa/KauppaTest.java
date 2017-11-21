
package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class KauppaTest {
    
    Pankki pankki;
    Viitegeneraattori vk;
    Varasto varasto;
    Kauppa kauppa;
    
    @Before
    public void setUp(){
        pankki = mock(Pankki.class);
        vk = mock(Viitegeneraattori.class);
        when(vk.uusi()).thenReturn(55);
        varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(5);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "laku", 5));
    }
    
    @Test
    public void ostosYhdellaTuotteella(){
        kauppa = new Kauppa(varasto, pankki, vk);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(5));
    }
    
    @Test
    public void ostosKahdellaEriTuotteella(){
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "maito", 2));
        
        kauppa = new Kauppa(varasto, pankki, vk);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        
        kauppa.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(7));
    }
    
    @Test
    public void ostosKahdellaSamallaTuotteella(){
        kauppa = new Kauppa(varasto, pankki, vk);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        
        kauppa.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(10));
        
    }
    
    @Test
    public void ostosKunToinenTuoteLoppu(){
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "maito", 2));
        
        kauppa = new Kauppa(varasto, pankki, vk);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        
        kauppa.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(5));
    }
    
    @Test
    public void uusiAsiointiNollaaEdellisen(){
        kauppa = new Kauppa(varasto, pankki, vk);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(5));
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("erkki", "258258");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("258258"), eq("33333-44455"), eq(5));
    }
    
    @Test
    public void uusiViiteJokaiselleTapahtumalle(){
        kauppa = new Kauppa(varasto, pankki, vk);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("erkki", "112558");
        
        verify(vk, times(1)).uusi();
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("erkki", "258258");
        
        verify(vk, times(2)).uusi();
    }
    
    @Test
    public void tuotteenVoiPoistaaKorista(){
        kauppa = new Kauppa(varasto, pankki, vk);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.poistaKorista(1);
        kauppa.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(5));
    }
}
