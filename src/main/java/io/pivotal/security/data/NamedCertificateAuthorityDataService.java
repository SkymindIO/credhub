package io.pivotal.security.data;

import io.pivotal.security.entity.NamedCertificateAuthority;
import io.pivotal.security.entity.SecretEncryptionHelper;
import io.pivotal.security.repository.NamedCertificateAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.UUID;

@Service
public class NamedCertificateAuthorityDataService {
  private final NamedCertificateAuthorityRepository namedCertificateAuthorityRepository;
  private final SecretEncryptionHelper secretEncryptionHelper;

  @Autowired
  public NamedCertificateAuthorityDataService(NamedCertificateAuthorityRepository namedCertificateAuthorityRepository,
                                              SecretEncryptionHelper secretEncryptionHelper) {
    this.namedCertificateAuthorityRepository = namedCertificateAuthorityRepository;
    this.secretEncryptionHelper = secretEncryptionHelper;
  }

  public NamedCertificateAuthority save(NamedCertificateAuthority certificateAuthority) {
    NamedCertificateAuthority save = namedCertificateAuthorityRepository.save(certificateAuthority);
    return save;
  }

  public NamedCertificateAuthority findMostRecentByName(String name) {
    return namedCertificateAuthorityRepository.findFirstByNameIgnoreCaseOrderByUpdatedAtDesc(name);
  }

  public NamedCertificateAuthority findOneByUuid(String uuid) {
    return namedCertificateAuthorityRepository.findOneByUuid(UUID.fromString(uuid));
  }

  public void updatePrivateKey(NamedCertificateAuthority certificateAuthority, String privateKey) {
    secretEncryptionHelper.refreshEncryptedValue(certificateAuthority, privateKey);
  }

  public String getPrivateKeyClearText(NamedCertificateAuthority certificateAuthority) {
    return secretEncryptionHelper.retrieveClearTextValue(certificateAuthority);
  }

  public List<NamedCertificateAuthority> findAllByName(String name) {
    return newArrayList();
  }
}